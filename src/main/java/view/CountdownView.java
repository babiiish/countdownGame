    package view;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.concurrent.*;

    /**
     * CountdownView handles the user interface for the Countdown Letter Game.
     */
    public class CountdownView {
        private final BufferedReader scanner;

        public CountdownView() {
            this.scanner = new BufferedReader(new InputStreamReader(System.in));
        }

        public enum MessageType {

            // Error Messages
            NO_WORDS,
            INVALID_WORD_FROM_LETTERS,
            WORD_NOT_ENGLISH,
            INVALID_CHOICE_MESSAGE,
            TOO_MANY_VOWELS_OR_CONSONANTS,

            // Game State Messages
            INTRODUCTION,
            DISPLAY_LETTERS,
            DISPLAY_ROUNDS,
            USER_WORD_SCORE,
            FINAL_RESULT,
        }

        /**
         * Displays game state such as introduction, round, round score and final game result.
         * @param messageType The type of result message to display.
         * @param args        Additional data such as the word entered, score, or total score.
         */
        public void displayGameState(MessageType messageType, String... args) {
            switch (messageType) {
                case INTRODUCTION:
                    System.out.println("Welcome to the Countdown Letter Game!");
                    break;
                case DISPLAY_LETTERS:
                    System.out.println("Your letters are: " + args[0]);
                    break;
                case DISPLAY_ROUNDS:
                    System.out.println("\nRound " + args[0] +"\n");
                    break;
                case USER_WORD_SCORE:
                    System.out.println("Longest word: " + args[2]);
                    System.out.println("You entered: " + args[0] + " (Score: " + args[1] + ")");
                    break;
                case FINAL_RESULT:
                    System.out.println("\nGame Over! Your total score is: " + args[0] + " out of " + args[1]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid game result message type: " + messageType);
            }
        }

        public String getVowelOrConsonantChoice() {
            String input = "";
            try {
                System.out.print("Do you want a vowel (v) or a consonant (c)? ");
                input = scanner.readLine().toLowerCase();
            } catch (IOException e) {
                System.out.println("Invalid input. Please enter c or v.");
            }
            return input;
        }

        public String getPlayerName() {
            String input = "";
            try {
                System.out.println("What is your name? ");
                input = scanner.readLine().toLowerCase();
            } catch (IOException ignored) {
            }
            return input;
        }

        /**
         * Displays error messages related to user input.
         *
         * @param messageType The type of error message to display.
         */
        public void displayErrorMessage(MessageType messageType) {
            switch (messageType) {
                case NO_WORDS:
                    System.out.println("\nNo word entered within the time limit.\n");
                    break;
                case INVALID_WORD_FROM_LETTERS:
                    System.out.println("\nInvalid word! Your word must be formed using the given letters.\n");
                    break;
                case WORD_NOT_ENGLISH:
                    System.out.println("\nInvalid word! The word is not found in the English dictionary.\n");
                    break;
                case TOO_MANY_VOWELS_OR_CONSONANTS:
                    System.out.println("\nYou have too many vowels or consonants!\n");
                    break;
                case INVALID_CHOICE_MESSAGE:
                    System.out.println("\nInvalid choice! You can only enter [c] or [v].\n");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid error message type: " + messageType);
            }
        }

        /**
         * Prompts user to enter the number of vowels.
         *
         * @return Integer inputted by user.
         */
        public int getVowels() {
            int numVowels;
            while (true) {
                System.out.println("Enter the number of vowels (1-3): ");
                try {
                    String input = scanner.readLine();
                    numVowels = Integer.parseInt(input.trim()); // Trim to remove extra spaces

                    if (numVowels >= 1 && numVowels <= 3) {
                        break;
                    }
                } catch (NumberFormatException | IOException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            }
            return numVowels;
        }

        /**
         * Prompts user to input a word.
         *
         * @return Word entered by the user
         * @throws IOException if there is an input/output error while reading the user input.
         */
        public String getWord() throws IOException {
            final String[] userInput = {null};

            System.out.print("\nEnter your word: \n");
            userInput[0] = scanner.readLine();

            return userInput[0].toLowerCase();
        }

        /**
         * Allows the user to enter a word with a time limit. If the user doesn't input a word within
         * the specified time, the input is cancelled, and an empty string is returned. Code is implemented with the help
         * of <a href="https://stackoverflow.com/questions/44038081/set-time-limit-on-user-input-scanner-java">...</a>
         *
         * @param timeoutSeconds Integer The time limit (in seconds) for the user to enter a word.
         * @return The word entered by the user, or an empty string if the time limit is exceeded.
         */
        public String getWordWithCountdown(Integer timeoutSeconds) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(() -> {
                System.out.println("\nYou have " + timeoutSeconds + " seconds to make a word!");
                System.out.println("Enter your word: ");
                while (true) { // Need this, as without it scanner.readline still waits for input even after timeout
                    try {
                        if (System.in.available() > 0) { // Checks if the user has inputted anything
                            return scanner.readLine();
                        }
                    } catch (IOException ignored) {}
                }
            });

            try {
                return future.get(timeoutSeconds, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                future.cancel(true);
                flushInputBuffer();
                return "";
            } catch (Exception e) {
                return "";
            } finally {
                executor.shutdownNow();
            }
        }

        /**
         * Flushes the input buffer to prevent any unintended carry-over of user input.
         * Removing unwanted input does not interfere with the next round.
         */
        private void flushInputBuffer() {
            try {
                while (System.in.available() > 0) {
                    System.in.read(); // Consume extra input
                }
            } catch (IOException ignored) {}
        }
    }




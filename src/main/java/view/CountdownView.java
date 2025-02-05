    package view;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.List;
    import java.util.concurrent.*;

    /**
     * CountdownView handles the user interface for the Countdown Letter Game.
     */
    public class CountdownView {
        private final BufferedReader scanner;

        public CountdownView() {
            this.scanner = new BufferedReader(new InputStreamReader(System.in));
        }

        /**
         * Displays the introduction at beginning of the game.
         */
        public void displayIntroduction() {
            System.out.println("Welcome to the Countdown Letter Game!");
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
         * Displays the letters generated for the user.
         */
        public void displayLetters(List<Character> letters) {
            System.out.println("Your letters are: " + letters);
        }

        /**
         * Displays the results for the round including score and longest word.
         */
        public void displayRoundResult(String longestWord, int score) {
            System.out.println("Longest word: " + longestWord);
            System.out.println("Total Score: " + score);
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

        public void displayFinalResult(int score, int possibleScore) {
            System.out.println("\nGame Over! Your total score is: " + score + " out of " + possibleScore);
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
                        if (scanner.ready()) { // Checks if the user has inputted anything
                            return scanner.readLine();
                        }
                    } catch (IOException ignored) {}
                }
            });

            try {
                return future.get(timeoutSeconds, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                future.cancel(true);
                return "";
            } catch (Exception e) {
                return "";
            } finally {
                executor.shutdownNow();
            }
        }
    }


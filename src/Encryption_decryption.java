import java.util.Random;
import java.util.Scanner;

class Main2 {

    //XOR is NOT done with ‘^’ since the operator compare bits in a character and fails to construct a binary cipher. Reason behind is char ‘1’ is 00110001 in bit form.
    public static void main(String[] args) {
        String key = "";
        //Read message from user
        Scanner scan = new Scanner(System.in);
        String message = scan.nextLine();
        System.out.println("Your message is: " + message);

        //Encode
        int t = message.length();
        //Generate a key
        StringBuilder keyBuilder = new StringBuilder();
        char[] binarySet = "01".toCharArray();
        for (int i = 0; i < t; i++) {
            int bit = new Random().nextInt(binarySet.length);
            keyBuilder.append(binarySet[bit]);
        }
        //Display one-time-key to check
        key = keyBuilder.toString();
        System.out.println("Key is: " + key);

        //Start XOR
        StringBuilder cipherBuilder = new StringBuilder();
        for (int i = 0; i < t; i++) {
            if (message.charAt(i) == key.charAt(i))
                cipherBuilder.append('0');
            else
                cipherBuilder.append('1');
        }
        String cipherText = cipherBuilder.toString();
        System.out.println("Your Vernam Cipher is: " + cipherText);
        //Encode end

        //Decode to display message is correctly encoded
        StringBuilder messageBuilder = new StringBuilder();

        //Start XOR
        for (int i = 0; i < t; i++) {
            if (cipherText.charAt(i) == key.charAt(i))
                messageBuilder.append('0');
            else
                messageBuilder.append('1');
        }
        String messageRebuilt = messageBuilder.toString();
        System.out.println("Your Rebuilt Message is: " + messageRebuilt);

        //Decode end
    }
}
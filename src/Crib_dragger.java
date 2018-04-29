import java.util.Random;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        //Message to be sent "loveMICS"
        String msg = "0110110001101111011101100110010101001101010010010100001101010011";
        System.out.println("To observe decryption w/o a key enter binary expression of loveMICS: "+ msg);
        //First 32bits of message "love"
        String m1 = "01101100011011110111011001100101";
        //Second 32bits of message "MICS"
        String m2 = "01001101010010010100001101010011";
        String key;
        //Read message from user
        Scanner scan = new Scanner(System.in);
        String message = scan.nextLine();
        System.out.println("Your message: " + message);

        //Encode
        int t = message.length();
        //Generate a key
        key = keyGen32(t);
        //Display one-time-key to check
        System.out.println("Key: " + key);

        //Start XOR
        StringBuilder cipherBuilder = new StringBuilder();
        for (int i = 0; i < t; i++) {
            if (message.charAt(i) == key.charAt(i))
                cipherBuilder.append('0');
            else
                cipherBuilder.append('1');
        }
        String cipherText = cipherBuilder.toString();
        System.out.println("Your Vernam Cipher: " + cipherText);
        //Encode end

        //Decode
        StringBuilder messageBuilder = new StringBuilder();

        //Start XOR
        for (int i = 0; i < t; i++) {
            if (cipherText.charAt(i) == key.charAt(i))
                messageBuilder.append('0');
            else
                messageBuilder.append('1');
        }
        String messageRebuilt = messageBuilder.toString();
        System.out.println("Your Rebuilt Message: " + messageRebuilt);

        //DECRYPTION STARTS
        //Divide cipherText into 32bit pieces if message is longer than 64bit
        if(t>=64){
            int d = t/32;
            String[] cipherTextPartList = new String[d];
            for(int i=0;i<d;i++){
                cipherTextPartList[i] = cipherText.substring(32*i,32*(i+1));
            }
            //Start xOR two ciphertext pieces to generate xORed plain text pieces such that c1⊕c2 = m1⊕m2⊕key⊕key = m1⊕m2⊕0 = m1⊕m2
            StringBuilder cipherXORBuilder = new StringBuilder();
            for (int i = 0; i < 32; i++) {
                if (cipherTextPartList[0].charAt(i) == cipherTextPartList[1].charAt(i))
                    cipherXORBuilder.append('0');
                else
                    cipherXORBuilder.append('1');
            }
            String c1XORc2 = cipherXORBuilder.toString();
            //Apply "crib dragging" method to sample message "loveMICS"
            //xOR c1XORc2 with love to obtain MICS and vice versa
            StringBuilder recoveredM1 = new StringBuilder();
            for(int i=0;i<32;i++){
                if(m2.charAt(i)==c1XORc2.charAt(i))
                    recoveredM1.append('0');
                else
                    recoveredM1.append('1');
            }
            StringBuilder recoveredM2 = new StringBuilder();
            for(int i=0;i<32;i++){
                if(m1.charAt(i)==c1XORc2.charAt(i))
                    recoveredM2.append('0');
                else
                    recoveredM2.append('1');
            }
            System.out.println("First part of your message, m1: "+recoveredM1.toString());
            System.out.println("Observe if m1 equals to the binary expression of \"love\":"+m1);
            System.out.println("Second part of your message: "+recoveredM2.toString());
            System.out.println("Observe if m2 equals to the binary expression of \"MICS\":"+m2);
        }
        //DECRYPTION ENDS
    }
    private static String keyGen32(int t){
        int r = t%32;
        int d = t/32;
        //Create 32-bit unique part
        StringBuilder keyBuilder = new StringBuilder();
        char[] binarySet = "01".toCharArray();
        if(t<=32){
            for(int i=0;i<t;i++){
                int bit = new Random().nextInt(binarySet.length);
                keyBuilder.append(binarySet[bit]);
            }
        }
        else{
            for(int i=0;i<32;i++){
                int bit = new Random().nextInt(binarySet.length);
                keyBuilder.append(binarySet[bit]);
            }
            String keyUniquePart = keyBuilder.toString();
            for(int i=0;i<d-1; i++)
                keyBuilder.append(keyUniquePart);
            for(int i=0;i<r;i++)
                keyBuilder.append(keyUniquePart.charAt(i));
        }
        return keyBuilder.toString();
    }
}
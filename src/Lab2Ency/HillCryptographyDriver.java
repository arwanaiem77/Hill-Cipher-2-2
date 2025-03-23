package Lab2Ency;

public class HillCryptographyDriver {
    public static void main(String[] args) {
        int[] alphaArr = new int[26];
        for (int i = 0; i < 26; i++) {
            alphaArr[i] = i; 
        }

        String key = "DDCF";
        int sizeOfArray = (int) Math.sqrt(key.length());
        double[][] arrNum = new double[sizeOfArray][sizeOfArray];

        int c = 0;
        for (int row = 0; row < sizeOfArray; row++) {
            for (int col = 0; col < sizeOfArray; col++) {
                arrNum[row][col] = alphaArr[key.charAt(c) - 65];
                c++;
            }
        }

        System.out.println("Key Matrix:");
        for (int row = 0; row < sizeOfArray; row++) {
            for (int col = 0; col < sizeOfArray; col++) {
                System.out.print(arrNum[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();

        String pt = "HELP";
        int blockSize = sizeOfArray;

        int[][] arrP1Num = new int[blockSize][1];
        int[][] arrP2Num = new int[blockSize][1];

        arrP1Num[0][0] = alphaArr[pt.charAt(0) - 65]; // H -> 7
        arrP1Num[1][0] = alphaArr[pt.charAt(1) - 65]; // E -> 4

        arrP2Num[0][0] = alphaArr[pt.charAt(2) - 65]; // L -> 11
        arrP2Num[1][0] = alphaArr[pt.charAt(3) - 65]; // P -> 15

        System.out.println("Plaintext Block 1 (HE):");
        for (int row = 0; row < blockSize; row++) {
            System.out.println(arrP1Num[row][0]);
        }

        System.out.println("Plaintext Block 2 (LP):");
        for (int row = 0; row < blockSize; row++) {
            System.out.println(arrP2Num[row][0]);
        }

        System.out.println("Encrypted Block 1:");
        char[] encryptedArray1 = encryption(arrNum, sizeOfArray, arrP1Num);

        System.out.println("Encrypted Block 2:");
        char[] encryptedArray2 = encryption(arrNum, sizeOfArray, arrP2Num);       
        char[] encryptedPlainText= new char[pt.length()];

        int j=0;
        while(j!=encryptedArray1.length) {
        		encryptedPlainText[j]=encryptedArray1[j];
        		j++;

        	}
        int l=0;
        while(l!=encryptedArray2.length) {
    		encryptedPlainText[j]=encryptedArray2[l];
    		j++;                                     
    		l++;

        }
        System.out.println("encrypted:");
        for(int i=0;i<encryptedPlainText.length;i++)
        	System.out.print( encryptedPlainText[i]);
        
        
        
        
        char [] ct = encryptedPlainText;

        int[][] arrC1Num = new int[blockSize][1];
        int[][] arrC2Num = new int[blockSize][1];

        arrC1Num[0][0] = alphaArr[ct[0] - 65]; // H -> 7
        arrC1Num[1][0] = alphaArr[ct[1] - 65]; // E -> 4

        arrC2Num[0][0] = alphaArr[ct[2] - 65]; // L -> 11
        arrC2Num[1][0] = alphaArr[ct[3] - 65]; // P -> 15

        System.out.println("Ciphertext Block 1 (HI):");
        for (int row = 0; row < blockSize; row++) {
            System.out.println(arrP1Num[row][0]);
        }

        System.out.println("Ciphertext Block 2 (AT):");
        for (int row = 0; row < blockSize; row++) {
            System.out.println(arrP2Num[row][0]);
        }
        
        
        
        double adj =(arrNum[0][0]*arrNum[1][1])-(arrNum[0][1]*arrNum[1][0]);
    	System.out.println(adj);
    	double [][] arrKeyInverse=calculateInvers(arrNum,sizeOfArray);
    	
    	  System.out.println("Key Matrix:");
          for (int row = 0; row < sizeOfArray; row++) {
              for (int col = 0; col < sizeOfArray; col++) {
                  System.out.print(arrKeyInverse[row][col] + " ");
              }
              System.out.println();
          }
          System.out.println();
          System.out.println("Decrypted Block 1:");
          char[] decryptedArray1 = decryption(arrKeyInverse, sizeOfArray, arrC1Num);

          System.out.println("Decrypted Block 2:");
          char[] decryptedArray2 = decryption(arrKeyInverse, sizeOfArray, arrC2Num);

          char[] decryptedPlainText = new char[pt.length()];
          System.arraycopy(decryptedArray1, 0, decryptedPlainText, 0, decryptedArray1.length);
          System.arraycopy(decryptedArray2, 0, decryptedPlainText, decryptedArray1.length, decryptedArray2.length);

          System.out.println("Decrypted Text:");
          System.out.println(new String(decryptedPlainText));          
        
        
        
        
    }

    public static char [] encryption(double[][] arrKey, int arrKeySize, int[][] arrP) {
    	double[] arrMulti = new double[arrKeySize]; // Initialize the result matrix

        for (int row = 0; row < arrKeySize; row++) {
            arrMulti[row] = 0;
            for (int k = 0; k < arrKeySize; k++) {
                arrMulti[row] += arrKey[row][k] * arrP[k][0];
            }
            arrMulti[row] = arrMulti[row] % 26; 
        }
        
        
        
        char [] encyChars=new char[arrKeySize];
        
        for (int row=0;row<arrKeySize;row++) {
        	encyChars[row]=(char) (arrMulti[row]+65);
        }

        for (int row = 0; row < encyChars.length; row++) {
                System.out.print(encyChars[row] + " ");
            
            System.out.println();
        }
        System.out.println();     
        return encyChars ;

    }
    public static char[] decryption(double[][] arrKey, int arrKeySize, int[][] arrP) {
        double[] arrMulti = new double[arrKeySize]; // Initialize the result matrix

        for (int row = 0; row < arrKeySize; row++) {
            arrMulti[row] = 0;
            for (int k = 0; k < arrKeySize; k++) {
                arrMulti[row] += arrKey[row][k] * arrP[k][0];
            }
            arrMulti[row] = arrMulti[row] % 26;
            if (arrMulti[row] < 0) {
                arrMulti[row] += 26; 
            }
        }

        char[] decryChars = new char[arrKeySize];
        for (int row = 0; row < arrKeySize; row++) {
            decryChars[row] = (char) (arrMulti[row] + 65); // Map to letters
        }

        for (int row = 0; row < decryChars.length; row++) {
            System.out.print(decryChars[row] + " ");
        }
        System.out.println();

        return decryChars;
    }
    

    public static double[][] calculateInvers(double[][] arrKey, int arrKeySize) {
    	double[][] arrInverse = new double[arrKeySize][arrKeySize];

        if (arrKeySize == 2) {
        	double a = arrKey[0][0];
        	double b = arrKey[0][1];
        	double c = arrKey[1][0];
        	double d = arrKey[1][1];

            
        	double det = (a * d) - (b * c);

           
            int detInverse = -1;
            for (int i = 1; i < 26; i++) {
                if ((det * i) % 26 == 1) {
                    detInverse = i;
                    break;
                }
            }

            if (detInverse == -1) {
                System.out.println("Matrix is not invertible (determinant has no modular inverse).");
                return null;
            }

          
            arrInverse[0][0] = (d * detInverse) % 26;
            arrInverse[0][1] = (-b * detInverse) % 26;
            arrInverse[1][0] = (-c * detInverse) % 26;
            arrInverse[1][1] = (a * detInverse) % 26;

           
            for (int row = 0; row < arrKeySize; row++) {
                for (int col = 0; col < arrKeySize; col++) {
                    if (arrInverse[row][col] < 0) {
                        arrInverse[row][col] += 26;
                    }
                }
            }

            return arrInverse;
        }

        return null; 
    }
}




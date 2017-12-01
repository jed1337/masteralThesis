package testUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Ignore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Ignore
public class TestUtils {
   public static final String DIGEST_ALGORITHM = "MD5";

   public static String getChecksumFromFile(String path)
           throws FileNotFoundException, NoSuchAlgorithmException, IOException {

      MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
      FileInputStream fis = new FileInputStream(path);

      byte[] dataBytes = new byte[1024];

      int nread = 0;
      while ((nread = fis.read(dataBytes)) != -1) {
         md.update(dataBytes, 0, nread);
      }

      return getChecksumFromDigest(md);
   }

   /**
    * Taken from: https://www.mkyong.com/java/java-md5-hashing-example/
    *
    * @param string to get the checksum from
    *
    * @return The checksum from the String
    *
    * @throws java.security.NoSuchAlgorithmException
    */
   public static String getChecksumFromString(String string) throws NoSuchAlgorithmException {
      MessageDigest md = MessageDigest.getInstance(DIGEST_ALGORITHM);
      md.update(string.getBytes());

      return getChecksumFromDigest(md);
   }

   private static String getChecksumFromDigest(MessageDigest md) {
      byte byteData[] = md.digest();

      StringBuilder hexString = new StringBuilder();
      for (int i = 0; i < byteData.length; i++) {
         String hex = Integer.toHexString(0xff & byteData[i]);
         if (hex.length() == 1) {
            hexString.append('0');
         }
         hexString.append(hex);
      }

      System.out.println("Digest(in hex format):: " + hexString.toString());
      return hexString.toString();
   }

   public static boolean deleteFile(String path) throws IOException {
      boolean wasDeleted = Files.deleteIfExists(Paths.get(path));
      
      if(!wasDeleted){
         System.err.println("Deletion successful.");
      }
      return wasDeleted;
   }

   private TestUtils() {}
}

package utils;

import java.sql.Statement;

public class UtilsDB {
   private UtilsDB() {
   }

   /**
    * Prints the status when multiple DB updates are made. Ideally, the output
    * should all be: "OK"
    * @param updateCounts
    */
   public static void checkUpdateCounts(int[] updateCounts) {
      for (int i = 0; i < updateCounts.length; i++) {
         if (updateCounts[i] >= 0) {
            System.out.println("OK; updateCount=" + updateCounts[i]);
         } else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
            System.out.println("OK; updateCount=Statement.SUCCESS_NO_INFO");
         } else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
            System.out.println("Failure; updateCount=Statement.EXECUTE_FAILED");
         }
      }
   }
}
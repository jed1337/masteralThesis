package preprocessFiles.utils;

public class MutableInt {
   int value;

   public MutableInt() {
      this.value = 1;
   }

   public void increment() {
      ++value;
   }

   /**
    * @return The value of the MutableInt
    */
   public int get() {
      return value;
   }
}

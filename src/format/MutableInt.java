package format;

public class MutableInt {
   int value;
   
   public MutableInt() {
      this(1);
   }
   
   public MutableInt(int value){
      this.value = value;
   }

   public void increment() {
      ++value;
   }

   public int get() {
      return value;
   }
}
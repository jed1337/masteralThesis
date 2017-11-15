package classify;

import format.MutableInt;

public class Counter{
   private final String attackType;
   private final String fileType;
   private final int limit;
   private final MutableInt cur;

   public Counter(String attackType, String fileType, int limit) {
      this.attackType = attackType;
      this.fileType = fileType;
      this.limit = limit;
      this.cur = new MutableInt(0);
   }
   
   public void increment(){
      this.cur.increment();
   }
   
   public boolean isFull(){
      return (this.cur.get() == this.limit);
   }

   public String getAttackType() {
      return attackType;
   }

   public String getFileType() {
      return fileType;
   }

   public int getLimit() {
      return limit;
   }

   public MutableInt getCur() {
      return cur;
   }
}
package train;

import java.io.IOException;

public abstract class TrainMultiple extends Train{
   private final String[] attackTypes;
   
   public TrainMultiple(int instancesCount, String fileName, String[] attackTypes) throws IOException {
      super(instancesCount, fileName);
      this.attackTypes = attackTypes;
   }
   
   @Override
   protected void removeNonMatchingClasses() {
      super.faa.removeNonMatchingClasses("service", "http");
   }
   
   @Override
   protected void keepXInstances() {
      for (String attackType : this.attackTypes) {
         super.faa.keepXInstances("isAttack", attackType, getDivider());
      }
   }
   
   private int getDivider(){
      return Math.round(super.instancesCount/this.attackTypes.length);
   }
}
package driver.mode;

import driver.categoricalType.SpecificAttackType;
import driver.mode.noiseLevel.NoNoise;

public abstract class SpecificAttack extends SystemType{
   public SpecificAttack(){
      super(NoNoise.getInstance(), new SpecificAttackType());
   }
   
   @Override
   public abstract String getSystemType();
}
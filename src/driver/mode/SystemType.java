package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseLevel;

public abstract class SystemType {
   private final CategoricalType categoricalType;
   private final NoiseLevel nl;
   
   protected SystemType(NoiseLevel nl, CategoricalType categoricalType){
      this.categoricalType = categoricalType;
      this.nl = nl;
   }
   
   public CategoricalType getCategoricalType(){
      return this.categoricalType;
   }
   
   public NoiseLevel getNoiseLevel(){
      return this.nl;
   }
   
	public abstract String getSystemType();
}
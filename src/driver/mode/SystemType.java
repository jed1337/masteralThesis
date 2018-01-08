package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseLevel;
import java.util.ArrayList;
import preprocessFiles.PreprocessFileBuilderStrategy;

public abstract class SystemType {
   protected final ArrayList<PreprocessFileBuilderStrategy> pfBS;
   private final CategoricalType categoricalType;
   private final NoiseLevel nl;
   
   protected SystemType(NoiseLevel nl, CategoricalType categoricalType){
      this.pfBS = new ArrayList<>();
      this.categoricalType = categoricalType;
      this.nl = nl;
   }
   
   public ArrayList<PreprocessFileBuilderStrategy> getPreprocessedFiles(){
      return this.pfBS;
   }
   
   public CategoricalType getCategoricalType(){
      return this.categoricalType;
   }
   
   public NoiseLevel getNoiseLevel(){
      return this.nl;
   }
   
	public abstract String getSystemType();
}
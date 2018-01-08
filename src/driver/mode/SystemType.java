package driver.mode;

import driver.categoricalType.CategoricalType;
import driver.mode.noiseLevel.NoiseLevel;
import java.util.ArrayList;
import preprocessFiles.PreprocessFile;

public abstract class SystemType {
   protected final ArrayList<PreprocessFile> pfL;
   private final CategoricalType categoricalType;
   private final NoiseLevel nl;
   
   protected SystemType(NoiseLevel nl, CategoricalType categoricalType){
      this.pfL = new ArrayList<>();
      this.categoricalType = categoricalType;
      this.nl = nl;
   }
   
   public ArrayList<PreprocessFile> getPreprocessedFiles(){
      return this.pfL;
   }
   
   public CategoricalType getCategoricalType(){
      return this.categoricalType;
   }
   
   public NoiseLevel getNoiseLevel(){
      return this.nl;
   }
   
	public abstract String getSystemType();
}
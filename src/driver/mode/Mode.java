package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import driver.categoricalType.CategoricalType;
import generalInterfaces.GetPreprocessFiles;

public abstract class Mode implements GetPreprocessFiles{
//   protected final int totalInstanceCount;
//   protected final List<PreprocessFile> pfL;
   
   private final NoiseLevel nl;
   private final CategoricalType categoricalType;

   public Mode(NoiseLevel nl, CategoricalType categoricalType) throws IOException {
//      this.pfL = new ArrayList<>();
      this.nl = nl;
      this.categoricalType = categoricalType;
      
//      this.pfL.addAll(this.nl.getPreprocessedFiles());
   }

   public NoiseLevel getNoiseLevel() {
      return this.nl;
   }

   public CategoricalType getCategoricalType() {
      return this.categoricalType;
   }
   
   public abstract String getSystemType();
   

//   public final List<PreprocessFile> getPreprocessFiles() throws IOException{
//      return Collections.unmodifiableList(this.pfL);
//   }
//   
//   public final float getNoiseLevelFloat(){
//      return this.nl.getNoiseLevelFloat();
//   }
//   
//   public final String getNoiseLevelString(){
//      return this.nl.getNoiseLevelString();
//   }
//   
//   public final String getRelabel(){
//      return this.categoricalType.getRelabelSpecificAttack(this.pfL);
//   }
//   
//   public final CategoricalTypeConstants getCategoricalType(){
//      return this.categoricalType.getCategoricalType();
//   }

}
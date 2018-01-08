package driver.mode;

import constants.CategoricalTypeConstants;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;
import driver.categoricalType.CategoricalType;

public abstract class Mode {
   protected final int totalInstanceCount;
   protected final List<PreprocessFile> pfL;
   
   private final NoiseLevel nl;
   private final CategoricalType categoricalType;

   public Mode(int totalCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      this.totalInstanceCount = totalCount;
      this.pfL = new ArrayList<>();
      this.nl = nl;
      this.categoricalType = categoricalType;
      
      this.pfL.addAll(this.nl.getPreprocessedFiles());
   }

   public final List<PreprocessFile> getPreprocessFiles() throws IOException{
      return Collections.unmodifiableList(this.pfL);
   }
   
   public final float getNoiseLevelFloat(){
      return this.nl.getNoiseLevelFloat();
   }
   
   public final String getNoiseLevelString(){
      return this.nl.getNoiseLevelString();
   }
   
   public final String getRelabel(){
      return this.categoricalType.getRelabel(this.pfL);
   }
   
   public final CategoricalTypeConstants getCategoricalType(){
      return this.categoricalType.getCategoricalType();
   }

   public abstract String getSystemType();
}
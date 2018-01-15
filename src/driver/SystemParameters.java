package driver;

import constants.CategoricalTypeConstants;
import driver.categoricalType.CategoricalType;
import driver.mode.Mode;
import driver.mode.noiseLevel.NoiseLevel;
import generalInterfaces.GetPreprocessFiles;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class SystemParameters implements GetPreprocessFiles{
   private final NoiseLevel nl;
   private final CategoricalType categoricalType;
   private final String systemType;
   
   private final List<PreprocessFile> pfL;
   
   private SystemParameters(Builder spb) throws IOException{
      this.nl = spb.mode.getNoiseLevel();
      this.categoricalType = spb.mode.getCategoricalType();
      this.systemType = spb.mode.getSystemType();
      
      this.pfL = new ArrayList<>();
      this.pfL.addAll(spb.mode.getPreprocessFiles());
      this.pfL.addAll(spb.mode.getNoiseLevel().getPreprocessFiles());
      
      this.categoricalType.setPreprocessFileCount(this.pfL, spb.instanceCount);
   }
   
   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(this.pfL);
   }

   public float getNoiseLevelFloat() {
      return this.nl.getNoiseLevelFloat();
   }

   public String getNoiseLevelString() {
      return this.nl.getNoiseLevelString();
   }

   public CategoricalTypeConstants getCategoricalType() {
      return this.categoricalType.getCategoricalType();
   }

   public String getRelabel() {
      return this.categoricalType.getRelabelSpecificAttack(this.pfL);
   }

   public String getSystemType() {
      return systemType;
   }
   
   public static class Builder{
      private final int instanceCount;
      private final Mode mode;
      
      public Builder(int instanceCount, Mode mode) {
         this.instanceCount = instanceCount;
         this.mode = mode;
      }
      public SystemParameters build() throws IOException{
         return new SystemParameters(this);
      }
   }
}
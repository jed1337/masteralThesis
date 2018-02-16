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
   
   /**
    * Responsible for configuring the amount of instances in PreprocessFiles
    * given the noise, categorical type, and system type
    * <p>
    * PreprocessFile list order<br>
    * Noise<br>
    * Not-noise
    * 
    * @param builder
    * @throws IOException 
    */
   private SystemParameters(SystemParameters.Builder builder) throws IOException{
      this.nl = builder.mode.getNoiseLevel();
      this.categoricalType = builder.mode.getCategoricalType();
      this.systemType = builder.mode.getSystemType();
      
      this.pfL = new ArrayList<>();
      this.pfL.addAll(this.nl.getPreprocessFiles());
      this.pfL.addAll(builder.mode.getPreprocessFiles());
      
      this.categoricalType.setPreprocessFileCount(this.pfL, builder.instanceCount);
      System.out.println("");
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
      return this.systemType;
   }
   
   /**
    * Responsible for building a SystemParameter object
    * (which has private access)<p>
    * Actually has no point since all the parameters are mandatory
    */
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
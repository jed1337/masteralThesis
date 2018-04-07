package driver;

import constants.CategoricalTypeConstants;
import constants.NoiseDatasetNames;
import driver.categoricalType.CategoricalType;
import driver.mode.Mode;
import driver.mode.noiseLevel.NoiseDataset;
import generalInterfaces.GetPreprocessFiles;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class SystemParameters implements GetPreprocessFiles{
   private final NoiseDataset nl;
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
    * @param instanceCount How many overall instances to keep
    * @param mode
    * @throws IOException
    */
   public SystemParameters(int instanceCount, Mode mode) throws IOException{
      this.nl              = mode.getNoiseLevel();
      this.categoricalType = mode.getCategoricalType();
      this.systemType      = mode.getSystemType();

      this.pfL = new ArrayList<>();
      this.pfL.addAll(this.nl.getPreprocessFiles());
      this.pfL.addAll(mode.getPreprocessFiles());

      this.categoricalType.setPreprocessFileCount(this.pfL, instanceCount);
      System.out.println("");
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      return Collections.unmodifiableList(this.pfL);
   }

   public NoiseDatasetNames getNoiseDatasetName() {
      return this.nl.getNoiseDatasetName();
   }

   public float getNoiseToAttackcRatio() {
      return this.categoricalType.normalToNoiseRatio(this.pfL);
   }

   public CategoricalTypeConstants getCategoricalType() {
      return this.categoricalType.getCategoricalTypeConstant();
   }

   public String getRelabel() {
      return this.categoricalType.getRelabelSpecificAttack(this.pfL);
   }

   public String getSystemType() {
      return this.systemType;
   }
}
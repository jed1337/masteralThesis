package driver.mode;

import constants.CategoricalTypeConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class Mode {
   private final List<PreprocessFile> pfL;
   private final SystemType st;

   public Mode(int totalInstanceCount, SystemType st) throws IOException {
      this.pfL = new ArrayList<>();
      this.st = st;
      
      this.pfL.addAll(this.st.getPreprocessedFiles());
      this.pfL.addAll(this.st.getNoiseLevel().getPreprocessedFiles());
      
      this.st.getCategoricalType().setPreprocessFileCount(this.pfL, totalInstanceCount);
   }
   
   public List<PreprocessFile> getPreprocessFiles() throws IOException{
      return Collections.unmodifiableList(this.pfL);
   }
   
   public float getNoiseLevelFloat(){
      return this.st.getNoiseLevel().getNoiseLevelFloat();
   }
   
   public String getNoiseLevelString(){
      return this.st.getNoiseLevel().getNoiseLevelString();
   }
   
   public String getRelabel(){
      return this.st.getCategoricalType().getRelabel(this.pfL);
   }
   
   public CategoricalTypeConstants getCategoricalType(){
      return this.st.getCategoricalType().getCategoricalType();
   }

   public String getSystemType() {
      return this.st.getSystemType();
   }
}
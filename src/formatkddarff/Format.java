package formatkddarff;

import java.io.IOException;

public abstract class Format {
   protected String saveFileName="";

   public String getSaveFileName() throws IOException {
      if(saveFileName.isEmpty()){
         throw new IOException("No save file name given");
      }
      return saveFileName;
   }

   public void setSaveFileName(String saveFileName) {
      this.saveFileName = saveFileName;
   }
}
package formatkddarff.classify;

import formatkddarff.ClassifierHolder;
import formatkddarff.utils.UtilsClssifiers;
import formatkddarff.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Classify {
   protected final String folderPath;
   protected final ArrayList<ClassifierHolder> classifiers;
   
   protected Classify(String folderPath) throws IOException, Exception {
      this.folderPath = folderPath;
      Utils.makeFolders(folderPath);
      
      classifiers = new ArrayList<>();
   }
   
   public void buildModel() throws Exception{
      for (ClassifierHolder ch : this.classifiers) {
         UtilsClssifiers.writeModel(ch);
      }
   };
   public abstract void evaluateModel() throws Exception;
}
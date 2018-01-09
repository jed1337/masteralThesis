package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.List;
import preprocessFiles.PreprocessFile;

public interface CategoricalType {
   /**
    * Returns the number of classes in pfL. 
    * What a class is depends on the implementing subclass of CategoricalType
    * @param pfL
    * @return 
    */
   public int getClassCount(List<PreprocessFile> pfL);
   
   /**
    * Relables the specific attack.<br>
    * For general TCPFlood, the specific attack type turns into "High rate"<br>
    * For HybridStageIsAttack Slowread, the specific attack type turns into "Attack"
    * @param pfL
    * @return 
    */
   public String getRelabel(List<PreprocessFile> pfL);
   
   /**
    * General or nominal
    * @return 
    */
   public CategoricalTypeConstants getCategoricalType();
   
   /**
    * Evenly splits the total instances among the instances. For example:<br>
    * TotalInstances = 3000 
    * <p>
    * ClassA a1= 500 instances <br>
    * ClassA a2= 500 instances <br>
    * ClassB b= 1000 instances <br>
    * ClassC c= 1000 instances <br>
    * @param pfL
    * @param totalInstanceCount 
    */
   public void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) ;
}
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
    * Relabels the specific attack type depending on the implementation.<p>
    * For example, the specific attack is "tcpFlood"<br>
    * Implementation = GeneralAttackType. tcpFlood turns into HIGH_RATE<br>
    * Implementation = SpecificAttackType. tcpFlood turns into tcpFlood (no change)<br>
    * @param pfL
    * @return "{@code <specific_attack_1>:<relabel_1>, <specific_attack_2>:<relabel_2>, ..., <specific_attack_N>:<relabel_N>}"
    */
   public String getRelabelSpecificAttack(List<PreprocessFile> pfL);
   
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
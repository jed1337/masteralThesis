package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.List;
import preprocessFiles.PreprocessFile;

public interface CategoricalType {
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
    * Evenly splits the totalInstanceCount among the instances
    * per class type. What the class type is depends
    * on the class implementing this interface. <br>
    * The standard behaviour is as follows, for example:<br>
    * TotalInstances = 3000 
    * <p>
    * ClassA a1= 333 instances <br>
    * ClassA a2= 333 instances <br>
    * ClassA a3= 333 instances <br>
    * ClassB b1= 500 instances <br>
    * ClassB b2= 500 instances <br>
    * ClassC c = 1000 instances <br>
    * @param pfL
    * @param totalInstancesCount 
    */
   public void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstancesCount) ;
}
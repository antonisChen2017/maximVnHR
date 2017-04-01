package cn.com.maxim.portal.leat;

 abstract class Empirix
{
//	protected cfgItem cfgItems=null;
    protected rowItem rowItems=null;
    protected Empirix superior;
    
  //  public 	Empirix (cfgItem OldcfgItem){
 //   	this.cfgItems=OldcfgItem;
  //  }
    
    public void SetSuperior(Empirix Oldsuperior){
    	this.superior=Oldsuperior;
    }
    abstract public void rowItemApplications(rowItem rowItems);
}

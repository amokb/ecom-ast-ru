package ru.ecom.mis.ejb.service.lpu;


/**
 */
public interface ILpuService {
    boolean canShowSubdivisions(long aLpuId) ;
    boolean canShowAreas(long aLpuId) ;
    /** Создание всех ЛПУ из ОМС */
    void createMisLpuFromOmcLpu(String aParam) ;
    void removeOtherEquipment(long aLpu, long aEquipment);
    void createOtherEquipment(long aLpu, long aEquipment);
    
}

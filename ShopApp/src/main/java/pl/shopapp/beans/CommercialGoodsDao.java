package pl.shopapp.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class CommercialGoodsDao
 */
@Stateful
@LocalBean
public class CommercialGoodsDao implements CommercialGoodsDaoRemote, CommercialGoodsDaoLocal {

    /**
     * Default constructor. 
     */
    public CommercialGoodsDao() {
        // TODO Auto-generated constructor stub
    }

}

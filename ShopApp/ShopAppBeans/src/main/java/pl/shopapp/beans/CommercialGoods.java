package pl.shopapp.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class CommercialGoods
 */
@Stateful
@LocalBean
public class CommercialGoods implements CommercialGoodsRemote, CommercialGoodsLocal {

    /**
     * Default constructor. 
     */
    public CommercialGoods() {
        // TODO Auto-generated constructor stub
    }

}

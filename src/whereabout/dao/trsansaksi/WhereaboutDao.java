/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whereabout.dao.trsansaksi;

import whereabout.model.transaksi.Whereabout;
import whereabout.view.PanelWhereabout;

/**
 *
 * @author khoerulAbu
 */
public interface WhereaboutDao {
    public void requestLeave(PanelWhereabout panelWhereabout); 
    public Whereabout findById(int id); 
}

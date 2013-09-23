//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (C) 2008-2009 Openbravo, S.L.
//    http://www.unicenta.net/unicentaopos
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.model.Column;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.PrimaryKey;
import com.openbravo.data.model.Row;
import com.openbravo.data.model.Table;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable2;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author adrianromero
 */
public class DiscountsUsePanel extends JPanelTable2 {
   
    private DiscountsUseEditor editor;
    private DiscountFilter filter;
    
    @Override
    protected void init() {  
        
        filter = new DiscountFilter();
        filter.init(app);
        filter.addActionListener(new ReloadActionListener());
        
        row = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field("DISCOUNT", Datas.STRING, Formats.STRING),
                new Field("PRODUCT", Datas.STRING, Formats.STRING),
                new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true),
                new Field(AppLocal.getIntString("label.prodbarcode"), Datas.STRING, Formats.STRING, false, true, true),
                new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true)
        );        
        Table table = new Table(
                "DISCOUNT_PROD",
                new PrimaryKey("ID"),
                new Column("DISCOUNT"),
                new Column("PRODUCT"));
         
        lpr = row.getListProvider(app.getSession(), 
                "SELECT COM.ID, COM.DISCOUNT, COM.PRODUCT, P.REFERENCE, P.CODE, P.NAME " +
                "FROM DISCOUNT_PROD COM, PRODUCTS P " +
                "WHERE COM.PRODUCT = P.ID AND COM.DISCOUNT = ?", filter);
        spr = row.getSaveProvider(app.getSession(), table);              
        
        editor = new DiscountsUseEditor(app, dirty);
    }
    
    @Override
    public void activate() throws BasicException {
        filter.activate();
        
        //super.activate();
        startNavigation();
        reload(filter);
    }

    @Override
    public Component getFilter(){
        return filter.getComponent();
    }
    
    @Override
    public EditorRecord getEditor() {
        return editor;
    }  
    
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.DiscountUse");
    } 
    
    private void reload(DiscountFilter filter) throws BasicException {
        DiscountInfo disc = filter.getDiscountInfoExt();
        editor.setInsertDiscount(disc); // must be set before load
        bd.setEditable(disc != null);
        bd.actionLoad();
    }
            
    private class ReloadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reload((DiscountFilter)((Component)e.getSource()).getParent());
            } catch (BasicException w) {
            }
        }
    }
    
    
}

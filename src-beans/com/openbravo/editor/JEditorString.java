//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2012 uniCenta
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

package com.openbravo.editor;

import com.openbravo.data.gui.MessageInf;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.comtel.javafx.fxKeyboard;
import java.awt.Point;

public class JEditorString extends JEditorText {
    
    /** Creates a new instance of JEditorString */
    public JEditorString() {
        super();
        m_jText.addMouseListener(new JEditorMouseListener());
    }
    
    @Override
    protected final int getMode() {
        return EditorKeys.MODE_STRING;
    }
        
    @Override
    protected int getStartMode() {
        return MODE_Abc1;
    }
    
    private class JEditorMouseListener implements MouseListener {
        
        public void mousePressed(MouseEvent e) {
            
        }

        public void mouseReleased(MouseEvent e) {
            
        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {
            
        }

        public void mouseClicked(MouseEvent e) {
            JEditorString parent = (JEditorString) e.getComponent().getParent().getParent();
            if (parent.isEnabled()) {
                Point location = parent.getLocationOnScreen();
                location.y+=10;
                location.x-=5;
                fxKeyboard.showKeyboard(location);
            } 
        }
        
    }
    
}


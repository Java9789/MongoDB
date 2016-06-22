package tvk.controllers;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel;
import tvk.models.AccessColPrueba;
import tvk.models.ColPrueba;
import tvk.view.App;

public class AppController implements ActionListener {
    private App application;
    private AccessColPrueba access;
    private boolean flag = true;
    
    public AppController(){        
        access = new AccessColPrueba();
        EventQueue.invokeLater(() -> {
            try {
                JFrame.setDefaultLookAndFeelDecorated(true);
                UIManager.setLookAndFeel(new SubstanceRavenGraphiteGlassLookAndFeel());
            } catch(UnsupportedLookAndFeelException e){
                System.err.println(e.getMessage());
            }
            application = new App();
            application.setVisible(true);
            start();
        }); 
    }        
    
    public void start(){
        ver_coleccion();
        application.btnGuardar.setActionCommand("Guardar");
        application.btnGuardar.addActionListener(this);
        application.btnEliminar.setActionCommand("Eliminar");
        application.btnEliminar.addActionListener(this);
        application.btnModificar.setActionCommand("Modificar");
        application.btnModificar.addActionListener(this);
        application.btnNuevo.setActionCommand("Nuevo");
        application.btnNuevo.addActionListener(this);
        application.tbDatos.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                modificar_documento();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        switch(command){
            case "Guardar":
                if(flag == true){
                    agregar_documento();
                } else {
                    actualizar_documento();
                }
                break;
            case "Eliminar":
                eliminar_documento();
                break;
            case "Modificar":
                modificar_documento();
                break;
            case "Nuevo":
                nuevo();
                break;
        }
    }    
    
    private void ver_coleccion(){
        Object[] row = new Object[6];
        access.ver_colleccion().stream().map((col) -> {
            row[0] = col.getId();
            return col;
        }).map((col) -> {
            row[1] = col.getNombre();
            return col;
        }).map((col) -> {
            row[2] = col.getApp();
            return col;
        }).map((col) -> {
            row[3] = col.getApm();
            return col;
        }).map((col) -> {
            row[4] = col.getSexo();
            return col;
        }).map((col) -> {
            row[5] = col.getCumpleaños();
            return col;
        }).forEach((_item) -> {
            application.m_datos.addRow(row);
        });
        application.tbDatos.setModel(application.m_datos);
    }
    
    private void agregar_documento(){
        String nombre = application.txtNombre.getText().trim();
        String app = application.txtApp.getText().trim();
        String apm = application.txtApm.getText().trim();
        String sexo = "";
        if(application.rbtnMasculino.isSelected()){
            sexo = "Masculino";
        } else if(application.rbtnFemenino.isSelected()){
            sexo = "Femenino";
        }
        String cumpleaños = application.txtCumpleaños.getText().trim();
        if(nombre.isEmpty() || app.isEmpty() || apm.isEmpty() || sexo.isEmpty() || cumpleaños.isEmpty()){
            mensaje_error("Completa todos los campos ._.");
        } else {
            access.insertar_documento(nombre, app, apm, sexo, cumpleaños);
            mensaje("Documento ingresado correctamente :D");
        }
        nuevo();
    }
    
    private void modificar_documento(){
        try {
            int fila = application.tbDatos.getSelectedRow();
            application.txtNombre.setText(application.m_datos.getValueAt(fila, 1).toString());
            application.txtApp.setText(application.m_datos.getValueAt(fila, 2).toString());
            application.txtApm.setText(application.m_datos.getValueAt(fila, 3).toString());
            if(application.m_datos.getValueAt(fila, 4).equals("Masculino")){
                application.rbtnMasculino.setSelected(true);
            } else if(application.m_datos.getValueAt(fila, 4).equals("Femenino")){
                application.rbtnFemenino.setSelected(true);
            }
            application.txtCumpleaños.setText(application.m_datos.getValueAt(fila, 5).toString());
            flag = false;
        } catch(Exception e){
            mensaje_error("Selecciona un documento ._.");
            nuevo();
        }
    }
    
    private void actualizar_documento(){
        String nombre = application.txtNombre.getText().trim();
        String app = application.txtApp.getText().trim();
        String apm = application.txtApm.getText().trim();
        String sexo = "";
        if(application.rbtnMasculino.isSelected()){
            sexo = "Masculino";
        } else if(application.rbtnFemenino.isSelected()){
            sexo = "Femenino";
        }
        String cumpleaños = application.txtCumpleaños.getText().trim();
        if(nombre.isEmpty() || app.isEmpty() || apm.isEmpty() || sexo.isEmpty() || cumpleaños.isEmpty()){
            mensaje_error("Completa todos los campos ._.");
        } else {
            access.actualizar_documento(application.m_datos.getValueAt(application.tbDatos.getSelectedRow(), 0).toString(), nombre, app, apm, sexo, cumpleaños);
            mensaje("Documento actualizado correctamente :D");
            nuevo();
        }        
    }
    
    private void eliminar_documento(){
        try {
            int fila = application.tbDatos.getSelectedRow();
            String _id = application.m_datos.getValueAt(fila, 0).toString();
            String nombre = application.m_datos.getValueAt(fila, 1).toString();
            access.eliminar_documento(_id, nombre);
        } catch(Exception e){
            mensaje_error("Selecciona un documento ._.");
        }
        nuevo();
    }
    
    private void nuevo(){
        application.txtNombre.setText("");
        application.txtApp.setText("");
        application.txtApm.setText("");
        application.txtCumpleaños.setText("");
        application.btnGroup.clearSelection(); 
        application.txtNombre.requestFocus();
        limpiar_tabla();
        ver_coleccion();
        flag = true;
    }
    
    private void limpiar_tabla(){
        int filas = application.tbDatos.getRowCount();
        for(int i=0;i<filas;i++){
            application.m_datos.removeRow(0);
        }
    }
    
    private void mensaje(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE, null);
    }
    
    private void mensaje_error(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Mensaje", JOptionPane.ERROR_MESSAGE, null);
    }    
    
}
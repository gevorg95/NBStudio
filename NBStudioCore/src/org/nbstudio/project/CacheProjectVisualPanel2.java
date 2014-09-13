/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nbstudio.project;

import com.intersys.objects.CacheException;
import com.intersys.objects.CacheQuery;
import com.intersys.objects.Database;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.Exceptions;

public final class CacheProjectVisualPanel2 extends JPanel implements DocumentListener {

    private final CacheProjectWizardPanel2 panel;
    private WizardDescriptor wizard;

    /**
     * Creates new form CacheProjectVisualPanel2
     */
    public CacheProjectVisualPanel2(final CacheProjectWizardPanel2 panel) {
        initComponents();
        this.panel = panel;

        ((JTextComponent) namespaceComboBox.getEditor().getEditorComponent()).getDocument().addDocumentListener(this);
        namespaceComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateProjects();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        namespaceComboBox = new javax.swing.JComboBox();
        namespaceLabel = new javax.swing.JLabel();
        projectComboBox = new javax.swing.JComboBox();
        projectLabel = new javax.swing.JLabel();
        codepageComboBox = new javax.swing.JComboBox();
        codepageLabel = new javax.swing.JLabel();

        namespaceLabel.setLabelFor(namespaceComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(namespaceLabel, org.openide.util.NbBundle.getMessage(CacheProjectVisualPanel2.class, "CacheProjectVisualPanel2.namespaceLabel.text")); // NOI18N

        projectComboBox.setEditable(true);

        projectLabel.setLabelFor(projectComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(projectLabel, org.openide.util.NbBundle.getMessage(CacheProjectVisualPanel2.class, "CacheProjectVisualPanel2.projectLabel.text")); // NOI18N

        codepageComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UTF-8" }));
        codepageComboBox.setEnabled(false);

        codepageLabel.setLabelFor(codepageComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(codepageLabel, org.openide.util.NbBundle.getMessage(CacheProjectVisualPanel2.class, "CacheProjectVisualPanel2.codepageLabel.text")); // NOI18N
        codepageLabel.setToolTipText(org.openide.util.NbBundle.getMessage(CacheProjectVisualPanel2.class, "CacheProjectVisualPanel2.codepageLabel.toolTipText")); // NOI18N
        codepageLabel.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namespaceLabel)
                    .addComponent(projectLabel)
                    .addComponent(codepageLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectComboBox, 0, 278, Short.MAX_VALUE)
                    .addComponent(namespaceComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(codepageComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namespaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namespaceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codepageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codepageLabel))
                .addContainerGap(195, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox codepageComboBox;
    private javax.swing.JLabel codepageLabel;
    private javax.swing.JComboBox namespaceComboBox;
    private javax.swing.JLabel namespaceLabel;
    private javax.swing.JComboBox projectComboBox;
    private javax.swing.JLabel projectLabel;
    // End of variables declaration//GEN-END:variables

    boolean valid(WizardDescriptor wizardDescriptor) {
        return true;
    }

    void store(WizardDescriptor d) {
        String nsp = (String) namespaceComboBox.getSelectedItem();
        d.putProperty("namespace", nsp);
        String projectName = (String) projectComboBox.getSelectedItem();
        d.putProperty("cacheProjectName", projectName);
    }

    void read(WizardDescriptor settings) {
        wizard = settings;
        List<String> nspList = (List<String>) settings.getProperty("WizardPanel_NSPList");
        namespaceComboBox.removeAllItems();
        for (String nsp : nspList) {
            namespaceComboBox.addItem(nsp);
        }
        String nsp = (String) settings.getProperty("namespace");
        if (nsp != null) {
            namespaceComboBox.setSelectedItem(nsp);
        }
        String projectName = (String) settings.getProperty("cacheProjectName");
        projectComboBox.setSelectedItem(projectName);
    }

    void updateProjects() {
        String nsp = (String) namespaceComboBox.getSelectedItem();
        if (nsp == null) {
            return;
        }
        Database conn = null;
        try {
            conn = CacheProjectWizardIterator.getConnection(wizard, nsp);
            projectComboBox.removeAllItems();
            projectComboBox.addItem(null);
            CacheQuery query = new CacheQuery(conn, "%Studio.Project", "ProjectList");
            ResultSet rs = query.execute();
            while (rs.next()) {
                String projectName = rs.getString("Name");
                projectComboBox.addItem(projectName);
            }
        } catch (CacheException | SQLException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            if (conn != null && conn.isOpen()) {
                try {
                    conn.close();
                } catch (CacheException ex) {

                }
            }

        }
    }

    @Override
    public void insertUpdate(DocumentEvent e
    ) {
        panel.fireChangeEvent();
    }

    @Override
    public void removeUpdate(DocumentEvent e
    ) {
        panel.fireChangeEvent();
    }

    @Override
    public void changedUpdate(DocumentEvent e
    ) {
        panel.fireChangeEvent();
    }

    void validate(WizardDescriptor d) throws WizardValidationException {
        // nothing to validate
    }
}
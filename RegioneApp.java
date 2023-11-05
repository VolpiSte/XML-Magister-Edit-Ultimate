import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RegioneApp extends JFrame {
    private ArrayList<Regione> regioni;
    private JTextArea outputTextArea;
    private JButton showDataButton;
    private JButton createRegioneButton;
    private JButton updateRegioneButton;
    private JButton deleteRegioneButton;
    private JButton readFromXMLButton;
    private JButton exportToXMLButton;

    public RegioneApp() {
        regioni = new ArrayList<>();
        setTitle("Sisma Apuane utlimate edition 🏭");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        outputTextArea = new JTextArea(15, 40);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        showDataButton = new JButton("Visualizza Dati");
        createRegioneButton = new JButton("Crea Regione");
        updateRegioneButton = new JButton("Aggiorna Regione");
        deleteRegioneButton = new JButton("Elimina Regione");
        readFromXMLButton = new JButton("Leggi da XML");
        exportToXMLButton = new JButton("Esporta in XML");

        //buttonPanel.add(showDataButton);
        buttonPanel.add(createRegioneButton);
        buttonPanel.add(updateRegioneButton);
        buttonPanel.add(deleteRegioneButton);
        buttonPanel.add(readFromXMLButton);
        buttonPanel.add(exportToXMLButton);

        add(buttonPanel, BorderLayout.SOUTH);

        showDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showData();
            }
        });
        
        createRegioneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCreateRegioneDialog();
            }
        });

        updateRegioneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUpdateRegioneDialog();
            }
        });

        deleteRegioneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDeleteRegioneDialog();
            }
        });

        readFromXMLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Inserisci il nome del file XML da cui leggere (senza estensione):");
                if (fileName != null && !fileName.isEmpty()) {
                    try {
                        // Carica i dati da XML e assegna l'elenco aggiornato
                        regioni = loadDataFromXML(fileName + ".xml");
                        showData(); // Aggiorna la visualizzazione dei dati
                    } catch (FileNotFoundException e1) {
                        System.out.println("File non trovato.");
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        exportToXMLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Inserisci il nome del file XML in cui esportare (senza estensione):");
                if (fileName != null && !fileName.isEmpty()) {
                    saveDataToXML(regioni, fileName + ".xml");
                }
            }
        });
    }
    

    private void showData() {
        outputTextArea.setText("");
        for (Regione regione : regioni) {
            String nomeRegione = regione.getLuogo();
            String nomeProvincia = regione.getProvince().getProa();
            Comune comune = regione.getProvince().getComuni();
            int numeroProprietapubblica = comune.getProprietaPubblica();
            int numeroProprietaPrivata = comune.getProprietaPrivata();
            String nomeClasse = comune.getClasse().getNome();

            String rowData = String.format("%-15s%-15s%-15s%-20d%-20d%-15s\n",
                    nomeRegione,
                    nomeProvincia,
                    comune.getNome(),
                    numeroProprietapubblica,
                    numeroProprietaPrivata,
                    nomeClasse);
            outputTextArea.append(rowData);
        }
    }

    private void showCreateRegioneDialog() {
        String nomeRegione = JOptionPane.showInputDialog("Inserisci il nome della Regione:");
        if (nomeRegione != null && !nomeRegione.isEmpty()) {
            String nomeProvincia = JOptionPane.showInputDialog("Inserisci il nome della Provincia:");
            if (nomeProvincia != null && !nomeProvincia.isEmpty()) {
                String nomeComune = JOptionPane.showInputDialog("Inserisci il nome del Comune:");
                if (nomeComune != null && !nomeComune.isEmpty()) {
                    String proprietaPubblicaStr = JOptionPane.showInputDialog("Inserisci il numero di Proprietà Pubbliche:");
                    String proprietaPrivataStr = JOptionPane.showInputDialog("Inserisci il numero di Proprietà Private:");
                    String nomeClasse = JOptionPane.showInputDialog("Inserisci il nome della Classe:");
    
                    try {
                        int numeroProprietapubblica = Integer.parseInt(proprietaPubblicaStr);
                        int numeroProprietaPrivata = Integer.parseInt(proprietaPrivataStr);
    
                        Provincia provincia = new Provincia(nomeProvincia, new Comune(nomeComune, numeroProprietapubblica, numeroProprietaPrivata, new Classe(nomeClasse)));
                        Regione nuovaRegione = new Regione(nomeRegione, provincia);
                        regioni.add(nuovaRegione);
    
                        showData(); // Aggiorna la visualizzazione dei dati
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Inserisci un numero valido per le proprietà.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Il nome del Comune non può essere vuoto.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Il nome della Provincia non può essere vuoto.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Il nome della Regione non può essere vuoto.");
        }
    }
    
    private void showUpdateRegioneDialog() {
        String nomeRegione = JOptionPane.showInputDialog("Inserisci il nome della Regione da aggiornare:");
        if (nomeRegione != null && !nomeRegione.isEmpty()) {
            boolean found = false;
            for (Regione regione : regioni) {
                if (regione.getLuogo().equals(nomeRegione)) {
                    found = true;
                    String nomeProvincia = JOptionPane.showInputDialog("Inserisci il nuovo nome della Provincia:");
                    if (nomeProvincia != null && !nomeProvincia.isEmpty()) {
                        String nomeComune = JOptionPane.showInputDialog("Inserisci il nuovo nome del Comune:");
                        if (nomeComune != null && !nomeComune.isEmpty()) {
                            String proprietaPubblicaStr = JOptionPane.showInputDialog("Inserisci il nuovo numero di Proprietà Pubbliche:");
                            String proprietaPrivataStr = JOptionPane.showInputDialog("Inserisci il nuovo numero di Proprietà Private:");
                            String nomeClasse = JOptionPane.showInputDialog("Inserisci il nuovo nome della Classe:");
    
                            try {
                                int numeroProprietapubblica = Integer.parseInt(proprietaPubblicaStr);
                                int numeroProprietaPrivata = Integer.parseInt(proprietaPrivataStr);
    
                                Provincia provincia = regione.getProvince();
                                provincia.setProa(nomeProvincia);
                                Comune comune = provincia.getComuni();
                                comune.setNome(nomeComune);
                                comune.setProprietaPubblica(numeroProprietapubblica);
                                comune.setProprietaPrivata(numeroProprietaPrivata);
                                comune.getClasse().setNome(nomeClasse);
    
                                showData(); // Aggiorna la visualizzazione dei dati
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Inserisci un numero valido per le proprietà.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Il nome del Comune non può essere vuoto.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Il nome della Provincia non può essere vuoto.");
                    }
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(this, "Regione non trovata.");
            }
        }
    }
    
    private void showDeleteRegioneDialog() {
        String nomeRegione = JOptionPane.showInputDialog("Inserisci il nome della Regione da eliminare:");
        if (nomeRegione != null && !nomeRegione.isEmpty()) {
            Regione regioneToRemove = null;
            for (Regione regione : regioni) {
                if (regione.getLuogo().equals(nomeRegione)) {
                    regioneToRemove = regione;
                    break;
                }
            }
            if (regioneToRemove != null) {
                regioni.remove(regioneToRemove);
                showData(); // Aggiorna la visualizzazione dei dati
            } else {
                JOptionPane.showMessageDialog(this, "Regione non trovata.");
            }
        }
    }

    public void saveDataToXML(ArrayList<Regione> data, String fileName) {
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileName))) {
            encoder.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Regione> loadDataFromXML(String fileName) throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new FileInputStream(fileName));
        ArrayList<Regione> data = new ArrayList<>();
        data = (ArrayList<Regione>)decoder.readObject();
        decoder.close();
        return data;
    }

    private void inserisciDatiIniziali() {
        Regione t1 = new Regione("Toscana", new Provincia("Lucca", new Comune("Lucca", 0, 8, new Classe("Ste1"))));
        Regione t2 = new Regione("Toscana", new Provincia("Massa Carrara", new Comune("Massa", 2, 0, new Classe("Ste2"))));
        Regione t3 = new Regione("Toscana", new Provincia("Pisa", new Comune("Pisa", 0, 4, new Classe("Ste3"))));
        Regione t4 = new Regione("Toscana", new Provincia("Pistoia", new Comune("Pistoia", 5, 7, new Classe("Ste4"))));
    
        regioni.add(t1);
        regioni.add(t2);
        regioni.add(t3);
        regioni.add(t4);
        
        showData(); // Aggiorna la visualizzazione dei dati
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegioneApp app = new RegioneApp();
            //app.inserisciDatiIniziali(); // Chiama il metodo per inserire i dati iniziali
            app.setVisible(true);
        });
    }
    
}

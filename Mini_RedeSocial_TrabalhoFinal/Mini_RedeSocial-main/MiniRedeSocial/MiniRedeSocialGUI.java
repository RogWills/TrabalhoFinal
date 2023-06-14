package MiniRedeSocial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Usuario {
    private String nome;
    private String email;
    private String senha;
    private List<String> emailsAmigos;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.emailsAmigos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public List<String> getEmailsAmigos() {
        return emailsAmigos;
    }

    public void adicionarAmigo(String emailAmigo) {
        emailsAmigos.add(emailAmigo);
    }

    public void removerAmigo(String emailAmigo) {
        emailsAmigos.remove(emailAmigo);
    }
}

class Sistema {
    private Map<String, Usuario> usuarios;
    private Usuario usuarioAtual;

    public Sistema() {
        this.usuarios = new HashMap<>();
        this.usuarioAtual = null;
    }
    public void cadastrarUsuario(String nome, String email, String senha) {
        Usuario usuario = new Usuario(nome, email, senha);
        usuarios.put(email, usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public void login(String email, String senha) {
        Usuario usuario = usuarios.get(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            usuarioAtual = usuario;
            System.out.println("Login efetuado com sucesso!");
        } else {
            System.out.println("Email ou senha incorretos. Tente novamente.");
        }
    }

    public void adicionarAmigo(String emailAmigo) {
        Usuario amigo = usuarios.get(emailAmigo);
        if (amigo != null) {
            usuarioAtual.adicionarAmigo(emailAmigo);
            System.out.println("Amigo adicionado com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    public void removerAmigo(String emailAmigo) {
        Usuario amigo = usuarios.get(emailAmigo);
        if (amigo != null) {
            usuarioAtual.removerAmigo(emailAmigo);
            System.out.println("Amigo removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    public List<String> pesquisarAmigos() {
        return usuarioAtual.getEmailsAmigos();
    }

    public void enviarMensagem(String emailAmigo, String mensagem) {
        Usuario amigo = usuarios.get(emailAmigo);
        if (amigo != null) {
            System.out.println("Mensagem enviada para " + amigo.getNome() + ": " + mensagem);
        } else {
            System.out.println("Amigo não encontrado.");
        }
    }
}

public class MiniRedeSocialGUI {
    private Sistema sistema;
    private JFrame frame;
    private JTextField nomeTextField;
    private JTextField emailTextField;
    private JPasswordField senhaPasswordField;
    private JTextField emailLoginTextField;
    private JPasswordField senhaLoginPasswordField;
    private JTextField emailAmigoTextField;
    private JTextArea amigosTextArea;

    public MiniRedeSocialGUI() {
        this.sistema = new Sistema();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 470, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Nome:");
        lblNewLabel.setBounds(20, 20, 80, 20);
        frame.getContentPane().add(lblNewLabel);

        nomeTextField = new JTextField();
        nomeTextField.setBounds(110, 20, 200, 20);
        frame.getContentPane().add(nomeTextField);
        nomeTextField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Email:");
        lblNewLabel_1.setBounds(20, 50, 80, 20);
        frame.getContentPane().add(lblNewLabel_1);

        emailTextField = new JTextField();
        emailTextField.setBounds(110, 50, 200, 20);
        frame.getContentPane().add(emailTextField);
        emailTextField.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Senha:");
        lblNewLabel_2.setBounds(20, 80, 80, 20);
        frame.getContentPane().add(lblNewLabel_2);

        senhaPasswordField = new JPasswordField();
        senhaPasswordField.setBounds(110, 80, 200, 20);
        frame.getContentPane().add(senhaPasswordField);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(110, 110, 100, 25);
        frame.getContentPane().add(btnCadastrar);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                String email = emailTextField.getText();
                String senha = new String(senhaPasswordField.getPassword());
                sistema.cadastrarUsuario(nome, email, senha);
            }
        });

        JLabel lblNewLabel_3 = new JLabel("Email:");
        lblNewLabel_3.setBounds(20, 170, 80, 20);
        frame.getContentPane().add(lblNewLabel_3);

        emailLoginTextField = new JTextField();
        emailLoginTextField.setBounds(110, 170, 200, 20);
        frame.getContentPane().add(emailLoginTextField);
        emailLoginTextField.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Senha:");
        lblNewLabel_4.setBounds(20, 200, 80, 20);
        frame.getContentPane().add(lblNewLabel_4);

        senhaLoginPasswordField = new JPasswordField();
        senhaLoginPasswordField.setBounds(110, 200, 200, 20);
        frame.getContentPane().add(senhaLoginPasswordField);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(110, 230, 100, 25);
        frame.getContentPane().add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailLoginTextField.getText();
                String senha = new String(senhaLoginPasswordField.getPassword());
                sistema.login(email, senha);
            }
        });

        JLabel lblNewLabel_5 = new JLabel("Email do amigo:");
        lblNewLabel_5.setBounds(20, 290, 100, 20);
        frame.getContentPane().add(lblNewLabel_5);

        emailAmigoTextField = new JTextField();
        emailAmigoTextField.setBounds(130, 290, 180, 20);
        frame.getContentPane().add(emailAmigoTextField);
        emailAmigoTextField.setColumns(10);

        JButton btnAdicionarAmigo = new JButton("Adicionar Amigo");
        btnAdicionarAmigo.setBounds(130, 320, 150, 25);
        frame.getContentPane().add(btnAdicionarAmigo);
        btnAdicionarAmigo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String emailAmigo = emailAmigoTextField.getText();
                sistema.adicionarAmigo(emailAmigo);
            }
        });

        JButton btnRemoverAmigo = new JButton("Remover Amigo");
        btnRemoverAmigo.setBounds(290, 320, 150, 25);
        frame.getContentPane().add(btnRemoverAmigo);
        btnRemoverAmigo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String emailAmigo = emailAmigoTextField.getText();
                sistema.removerAmigo(emailAmigo);
            }
        });

        JButton btnPesquisarAmigos = new JButton("Pesquisar Amigos");
        btnPesquisarAmigos.setBounds(130, 350, 150, 25);
        frame.getContentPane().add(btnPesquisarAmigos);
        btnPesquisarAmigos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String> amigos = sistema.pesquisarAmigos();
                amigosTextArea.setText("");
                for (String amigo : amigos) {
                    amigosTextArea.append(amigo + "\n");
                }
            }
        });

        JLabel lblNewLabel_6 = new JLabel("Lista de amigos:");
        lblNewLabel_6.setBounds(320, 25, 100, 20);
        frame.getContentPane().add(lblNewLabel_6);

        amigosTextArea = new JTextArea();
        amigosTextArea.setEditable(false);
        amigosTextArea.setBounds(320, 50, 120, 250);
        frame.getContentPane().add(amigosTextArea);

        JLabel lblNewLabel_7 = new JLabel("Mensagem:");
        lblNewLabel_7.setBounds(20, 380, 100, 20);
        frame.getContentPane().add(lblNewLabel_7);

        JTextField mensagemTextField = new JTextField();
        mensagemTextField.setBounds(130, 380, 250, 20);
        frame.getContentPane().add(mensagemTextField);
        mensagemTextField.setColumns(10);

        JButton btnEnviarMensagem = new JButton("Enviar Mensagem");
        btnEnviarMensagem.setBounds(130, 410, 150, 25);
        frame.getContentPane().add(btnEnviarMensagem);
        btnEnviarMensagem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String emailAmigo = emailAmigoTextField.getText();
                String mensagem = mensagemTextField.getText();
                sistema.enviarMensagem(emailAmigo, mensagem);
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }
}

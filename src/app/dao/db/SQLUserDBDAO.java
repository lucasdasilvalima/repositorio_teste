package app.dao.db;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import app.dao.DAOProperties;
import app.dao.IUserDAO;
import app.dao.ProcedureStatement;
import app.dao.exceptions.DAOException;
import app.dao.exceptions.LoginException;
import app.model.User;
import app.properties.UserProperty;
import app.util.Encryption;

/**
 * This class implements the methods of <code>IUserDAO</code>.
 *
 * @see IUserDAO for more information.
 *
 * @author lucasrafael
 */
public class SQLUserDBDAO implements IUserDAO, Encryption, ProcedureStatement {

    @Override
    public List<User> getAll(String nameLike) throws DAOException {
        throw new UnsupportedOperationException("Este recurso não está disponível");
    }

    @Override
    public User load(int id, String password) throws LoginException, DAOException {
        throw new UnsupportedOperationException("Este recurso não está disponível");
    }

    @Override
    public User load(String email, String pass)
            throws LoginException, DAOException
    {
        try (Connection conn = ConnectionFactory.openConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM Users WHERE email=?"
            );
            stmt.setString(1, email);

            ResultSet r = stmt.executeQuery();
            if (r.next()) {
                String password = decrypt(r.getString("password"));
                if (!pass.equals(password)) {
                    throw new LoginException("A senha digitada está incorreta.");
                }

                User user = new User();
                user.setName(r.getString("name"));
                user.setEmail(r.getString("email"));
                user.setPassword(password);
                return user;
            } else {
                throw new LoginException("Usuário não encontrado.");
            }

        } catch (SQLException sqle) {
            System.err.println(sqle);
            if (sqle.getErrorCode() == 0) {
                throw new LoginException("Erro desconhecido. Verifique sua " +
                        "conexão");
            } else {
                throw new DAOException(sqle.getMessage());
            }
        }
    }

    @Override
    public void delete(String id) throws DAOException {
        throw new UnsupportedOperationException("Este recurso não está disponível");
    }

    @Override
    public void update(User user) throws DAOException {
        throw new UnsupportedOperationException("Este recurso não está disponível");
//        try (Connection conn = ConnectionFactory.openConnection()) {
//            PreparedStatement stmt = conn.prepareStatement(
//                    "SELECT * FROM Users WHERE email=?"
//            );
//            stmt.setString(1, user.getEmail());
//
//            conn.setAutoCommit(false);
//            stmt.setString(3, user.getName());
//            stmt.setString(4, user.getEmail());
//            stmt.setString(6, encrypt(user.getPassword()));
//            stmt.executeUpdate();
//            conn.commit();
//
//        } catch (SQLException sqle) {
//            switch (sqle.getErrorCode()) {
//                case 0:
//                    throw new DAOException("Erro desconhecido. Verifique sua " +
//                            "conexão com o banco de dados");
//                case 1048:
//                    throw new DAOException("Alguns dados necessários do " +
//                            "usuário estão ausentes.");
//                default:
//                    throw new DAOException(sqle.getMessage());
//            }
//        }
    }

    @Override
    public void store(User user) throws DAOException {
        try (Connection conn = ConnectionFactory.openConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Users (name, email, password) VALUES (?, ?, ?);"
            );
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, encrypt(user.getPassword()));
            stmt.executeUpdate();

        } catch (SQLException sqle) {
            System.err.println(sqle);
            System.err.println(sqle.getErrorCode());
            switch (sqle.getErrorCode()) {
                case 1048:
                    throw new DAOException("Alguns dados necessários do " +
                            "usuário estão ausentes.");
                case 1062:
                    throw new DAOException("O usuário já " +
                            "está cadastrado.");
                default:
                    throw new DAOException(sqle.getMessage());
            }
        }
    }

    @Override
    public void insertUserImage(String id, String imagePath)
            throws DAOException {
        throw new UnsupportedOperationException("Este recurso não está disponível");
//        try (Connection conn = ConnectionFactory.openConnection()) {
//            conn.setAutoCommit(false);
//            String procedureStmt = prepareProcedure(DAOProperties.getInsertUserImageProcedure(),
//                    "?", "?" , "?");
//            PreparedStatement stmt = conn.prepareStatement(procedureStmt);
//            stmt.setString(1, id);
//            stmt.setString(3, imagePath);
//            stmt.executeUpdate();
//            conn.commit();
//
//        } catch (SQLException sqle) {
//            throw new DAOException(sqle.getMessage());
//        }
    }

    @Override
    public File getUserImage(String id) throws DAOException {
        throw new UnsupportedOperationException("Este recurso não está disponível");
//        try (Connection conn = ConnectionFactory.openConnection()) {
//            PreparedStatement stmt = conn.prepareStatement(
//                    "SELECT caminho FROM imagem WHERE id=" +
//                            "(SELECT imagem_id FROM " +
//                            " WHERE ra_id=?)"
//            );
//            stmt.setString(1, id);
//            ResultSet r = stmt.executeQuery();
//
//            String path;
//            if (r.next()) {
//                path = r.getString("caminho");
//            } else {
//                return null;
//            }
//            return new File(path);
//
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//            throw new DAOException(sqle.getMessage());
//        }
    }

    public static void main(String[] args) throws DAOException {
//        User u = new User();
//        u.setEmail("teste@email.com");
//        u.setName("teste");
//        u.setPassword("teste1234");
//        SQLUserDBDAO sqlUserDBDAO = new SQLUserDBDAO();
//        sqlUserDBDAO.store(u);

//        SQLUserDBDAO sqlUserDBDAO = new SQLUserDBDAO();
//        User u = sqlUserDBDAO.load("teste@email.com", "teste1234");
//        System.out.println(u);
    }
}

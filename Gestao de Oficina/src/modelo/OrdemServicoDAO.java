package banco;

import modelo.OrdemServico;
import modelo.Peca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {

    public void salvar(OrdemServico os) {
        String sqlOS = "INSERT INTO ordens_servico (veiculo_placa, valor_total, status) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Inicia transação

            try (PreparedStatement stmtOS = conn.prepareStatement(sqlOS)) {
                stmtOS.setString(1, os.getVeiculo().getPlaca());
                stmtOS.setDouble(2, os.getValorTotal());
                stmtOS.setString(3, os.getStatus());

                ResultSet rs = stmtOS.executeQuery();
                if (rs.next()) {
                    os.setId(rs.getInt(1));
                }

                // Salva as peças vinculadas na tabela intermediária
                String sqlItens = "INSERT INTO itens_os (os_id, peca_id) VALUES (?, ?)";
                try (PreparedStatement stmtItens = conn.prepareStatement(sqlItens)) {
                    for (Peca p : os.getPecas()) {
                        stmtItens.setInt(1, os.getId());
                        stmtItens.setInt(2, p.getId());
                        stmtItens.addBatch();
                    }
                    stmtItens.executeBatch();
                }

                conn.commit(); // Finaliza com sucesso
            } catch (SQLException e) {
                conn.rollback(); // Desfaz tudo se der erro
                throw new RuntimeException("Erro ao salvar OS: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro de conexão: " + e.getMessage());
        }
    }
}
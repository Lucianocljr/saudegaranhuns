/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpe.garanhuns.sg.model.dao.hibernate;

import java.util.List;
import br.edu.ifpe.garanhuns.sg.model.dao.interfaces.AtendimentoDAO;
import br.edu.ifpe.garanhuns.sg.model.pojo.Atendimento;
import br.edu.ifpe.garanhuns.sg.model.pojo.PostoSaude;
import org.hibernate.Session;
import br.edu.ifpe.garanhuns.sg.util.HibernateUtil;

/**
 *
 * @author Hérikles
 */
public class AtendimentoHibernate implements AtendimentoDAO {

    @Override
    public void inserir(Atendimento o) {
        Session session = HibernateUtil.getSession();
        PostoSaudeHibernate ph = new PostoSaudeHibernate();
        try {
            session.beginTransaction();
            PostoSaude ps = ph.recuperarPorNome(o.getPostoSaude().getNome());
            if (ps == null) {
                ph.inserir(o.getPostoSaude());
            } else {
                o.setPostoSaude(ps);
            }
            session.save(o);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.err.println("Falha ao salvar Atendimento. Erro: " + e.toString());
        } finally {
            session.close();
        }
    }

    @Override
    public void atualizar(Atendimento o) {
        Session session = HibernateUtil.getSession();
        PostoSaudeHibernate ph = new PostoSaudeHibernate();
        try {
            session.beginTransaction();
            PostoSaude ps = ph.recuperarPorNome(o.getPostoSaude().getNome());
            if (ps == null) {
                ph.inserir(o.getPostoSaude());
            } else {
                o.setPostoSaude(ps);
            }
            session.update(o);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.err.println("Falha ao alterar Atendimento. Erro: " + e.toString());
        } finally {
            session.close();
        }
    }

    @Override
    public void deletar(Atendimento o) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.delete(o);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.err.println("Falha ao remover Atendimento. Erro: " + e.toString());
        } finally {
            session.close();
        }
    }

    @Override
    public Atendimento recuperar(Integer id) {
        Session session = HibernateUtil.getSession();
        try {
            return (Atendimento) session.get(Atendimento.class.getName(), id);
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.err.println("Falha ao recuperar Atendimento. Erro: " + e.toString());
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Atendimento> recuperarTodos() {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            List<Atendimento> lista = session.createQuery("from " + Atendimento.class.getName()).list();
            session.getTransaction().commit();
            return lista;
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.err.println("Falha ao recuperar todos os Atendimentos. Erro: " + e.toString());
        } finally {
            session.close();
        }
        return null;
    }

}
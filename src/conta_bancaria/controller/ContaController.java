package conta_bancaria.controller;

import java.util.ArrayList;
import java.util.Optional;

import conta_bancaria.model.Conta;
import conta_bancaria.repository.ContaRepository;

public class ContaController implements ContaRepository {

    // Lista que armazena todas as contas bancárias em memória
    private ArrayList<Conta> listaContas = new ArrayList<Conta>();
    
    // Atributo usado para gerar números de conta sequenciais e únicos
    private int numero = 0;

    @Override
    public void procurarPorNumero(int numero) {
        // Busca a conta na lista usando o método auxiliar
        var conta = buscarNaCollection(numero);

        // Se a conta for encontrada, exibe os dados dela utilizando o método visualizar()
        if (conta.isPresent()) {
            conta.get().visualizar();
        } else {
            System.out.println("\nA Conta número: " + numero + " não foi encontrada!");
        }
    }

    @Override
    public void listarTodas() {
        if (listaContas.isEmpty()) {
            System.out.println("\nNão existem contas cadastradas!");
        } else {
            for (var conta : listaContas) {
                conta.visualizar();
            }
        }
    }

    @Override
    public void cadastrar(Conta conta) {
        listaContas.add(conta);
        System.out.println("\nA Conta número: " + conta.getNumero() + " foi criada com sucesso!");
    }

    @Override
    public void atualizar(Conta conta) {
        var buscaConta = buscarNaCollection(conta.getNumero());

        if (buscaConta.isPresent()) {
            // Substitui a conta antiga pela nova usando o índice correto
            listaContas.set(listaContas.indexOf(buscaConta.get()), conta);
            System.out.println("\nA Conta número: " + conta.getNumero() + " foi atualizada com sucesso!");
        } else {
            System.out.println("\nA Conta número: " + conta.getNumero() + " não foi encontrada!");
        }
    }    
    @Override
    public void atualizar(Conta conta) {

        Optional<Conta> buscaConta = buscarNaCollection(conta.getNumero());

        if (buscaConta.isPresent()) {
            listaContas.set(listaContas.indexOf(buscaConta.get()), conta);
            System.out.printf("\nA conta número %d foi atualizada com sucesso!", conta.getNumero());
        } else {
            System.out.println("\nA Conta número: " + numero + " não foi encontrada!");

    }
        
    }

    @Override
    public void deletar(int numero) {
        var conta = buscarNaCollection(numero);

        if (conta.isPresent()) {
            if (listaContas.remove(conta.get()))
                System.out.println("\nA Conta número: " + numero + " foi deletada com sucesso!");
        } else {
            System.out.println("\nA Conta número: " + numero + " não foi encontrada!");
        }
    }

    @Override
    public void sacar(int numero, float valor) {
        var conta = buscarNaCollection(numero);

        if (conta.isPresent()) {
            if (conta.get().sacar(valor))
                System.out.println("\nO Saque na Conta número: " + numero + " foi efetuado com sucesso!");
        } else {
            System.out.println("\nA Conta número: " + numero + " não foi encontrada!");
        }
    }

    @Override
    public void depositar(int numero, float valor) {
        var conta = buscarNaCollection(numero);

        if (conta.isPresent()) {
            conta.get().depositar(valor);
            System.out.println("\nO Depósito na Conta número: " + numero + " foi efetuado com sucesso!");
        } else {
            System.out.println("\nA Conta número: " + numero + " não foi encontrada!");
        }
    }

    @Override
    public void transferir(int numeroOrigem, int numeroDestino, float valor) {
        var contaOrigem = buscarNaCollection(numeroOrigem);
        var contaDestino = buscarNaCollection(numeroDestino);

        if (contaOrigem.isPresent() && contaDestino.isPresent()) {
            if (contaOrigem.get().sacar(valor)) {
                contaDestino.get().depositar(valor);
                System.out.println("\nA Transferência foi efetuada com sucesso!");
            }
        } else {
            System.out.println("\nA Conta de Origem e/ou Destino não foram encontradas!");
        }
    }

    // Gera um novo número sequencial para as novas contas
    public int gerarNumero() {
        return ++numero;
    }

    // Método auxiliar para buscar uma conta na lista pelo seu número
    public Optional<Conta> buscarNaCollection(int numero) {
        for (var conta : listaContas) {
            if (conta.getNumero() == numero)
                return Optional.of(conta);
        }
        return Optional.empty();
    }
}
package br.com.etecia.epictask.controller;

import br.com.etecia.epictask.model.Produto;
import br.com.etecia.epictask.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    // Lista todos os produtos
    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        List<Produto> todos = repository.findAll();
        model.addAttribute("produtos", todos);
        model.addAttribute("titulo", "Todos os Alimentos");
        return "produtos";
    }

    // Formulário para cadastrar novo produto
    @GetMapping("/produtos/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "cadastro";
    }

    // Salvar produto no banco
    @PostMapping("/produtos")
    public String salvarProduto(Produto produto) {
        repository.save(produto);
        return "redirect:/produtos";
    }

    // Produtos disponíveis (quantidade > 0)
    @GetMapping("/produtos/disponiveis")
    public String produtosDisponiveis(Model model) {
        List<Produto> disponiveis = repository.findByQuantidadeGreaterThan(0);
        model.addAttribute("produtos", disponiveis);
        model.addAttribute("titulo", "Alimentos Disponíveis");
        return "produtos";
    }

    // Produtos em falta (quantidade = 0)
    @GetMapping("/produtos/falta")
    public String produtosEmFalta(Model model) {
        List<Produto> falta = repository.findByQuantidadeEquals(0);
        model.addAttribute("produtos", falta);
        model.addAttribute("titulo", "Alimentos em Falta");
        return "produtos";
    }

    // Produtos próximos do vencimento (nos próximos 3 dias)
    @GetMapping("/produtos/vencendo")
    public String produtosVencendo(Model model) {
        LocalDate hojeMais3Dias = LocalDate.now().plusDays(3);
        List<Produto> vencendo = repository.findByValidadeBefore(hojeMais3Dias);
        model.addAttribute("produtos", vencendo);
        model.addAttribute("titulo", "Produtos Próximos do Vencimento");
        return "produtos";
    }

}

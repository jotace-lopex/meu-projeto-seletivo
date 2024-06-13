package indústria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 05, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 05, 02), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 01, 05), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 03, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 07, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 05, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(f -> f.getNome().equals("João"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("=== Imprimir todos os funcionários com todas suas informações ===");
        for (Funcionario f : funcionarios) {
            String dataNascimentoFormatada = f.getDataNascimento().format(formatter);
            System.out.println(f.toString() + ", Data de Nascimento: " + dataNascimentoFormatada);
        }

        for (Funcionario f : funcionarios) {
            f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")));
        }

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n=== Imprimir os funcionários, agrupados por função ===");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> {
                String dataNascimentoFormatada = f.getDataNascimento().format(formatter);
                System.out.println(f.toString() + ", Data de Nascimento: " + dataNascimentoFormatada);
            });
        });

        System.out.println("\n=== Imprimir os funcionários que fazem aniversário no mês 10 e 12 ===");
        funcionarios.stream()
            .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
            .forEach(f -> {
                String dataNascimentoFormatada = f.getDataNascimento().format(formatter);
                System.out.println(f.toString() + ", Data de Nascimento: " + dataNascimentoFormatada);
            });

        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(f -> f.getDataNascimento()));
        System.out.println("\n=== Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade ===");
        Period idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now());
        System.out.println("Funcionário mais velho: " + maisVelho.getNome() + ", Idade: " + idade.getYears());

        System.out.println("\n=== Imprimir a lista de funcionários por ordem alfabética ===");
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));
        funcionariosOrdenados.forEach(f -> {
            String dataNascimentoFormatada = f.getDataNascimento().format(formatter);
            System.out.println(f.toString() + ", Data de Nascimento: " + dataNascimentoFormatada);
        });

        BigDecimal totalSalarios = funcionarios.stream()
            .map(Funcionario::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n=== Imprimir o total dos salários dos funcionários ===");
        System.out.println("Total dos salários: " + totalSalarios);

        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n=== Imprimir quantos salários mínimos ganha cada funcionário ===");
        funcionarios.forEach(f -> {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }
}

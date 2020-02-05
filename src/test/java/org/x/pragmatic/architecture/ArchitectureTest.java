package org.x.pragmatic.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import org.springframework.transaction.annotation.Transactional;
import org.x.pragmatic.repository.Repository;
import org.x.pragmatic.usecase.Request;
import org.x.pragmatic.usecase.Response;
import org.x.pragmatic.usecase.UseCase;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;


@AnalyzeClasses(packages = "org.x.pragmatic")
public class ArchitectureTest {

    @ArchTest
    static ArchRule repositories_should_be_suffixed_and_should_implement_repository =
            classes()
                    .that().resideInAPackage("..repository..")
                    .and(DescribedPredicate.describe("except Repository itself", cl -> !cl.isEquivalentTo(Repository.class)))
                    .and(DescribedPredicate.describe("except test classes", cl -> !cl.getName().endsWith("Test")))
                    .should().implement(Repository.class)
                    .andShould().haveSimpleNameEndingWith("Repository");

    @ArchTest
    static ArchRule use_cases_should_implement_use_case =
            classes()
                    .that().resideInAnyPackage("..usecase..")
                    .and(DescribedPredicate.describe("except UseCase itself",
                            cl -> !cl.isEquivalentTo(UseCase.class) && !cl.isEquivalentTo(Request.class) && !cl.isEquivalentTo(Response.class)))
                    .should().implement(UseCase.class);
    @ArchTest
    static ArchRule use_case_should_be_transactional =
            methods()
            .that().areDeclaredInClassesThat().implement(UseCase.class)
            .and().arePublic()
            .should().beAnnotatedWith(Transactional.class);

    @ArchTest
    static ArchRule resources_should_be_suffixed =
            classes()
            .that().resideInAPackage("..resource..")
            .should().haveSimpleNameEndingWith("Resource");

    @ArchTest
    static ArchRule requests_should_implement_request =
            classes()
                    .that().resideInAnyPackage("..request..")
                    .should().implement(Request.class);

    @ArchTest
    static ArchRule responses_should_implement_response =
            classes()
                    .that().resideInAnyPackage("..response..")
                    .should().implement(Response.class);

    @ArchTest
    static ArchRule use_cases_should_depend_only_on_repositories_services_and_mappers =
            classes()
            .that().resideInAPackage("..usecase..")
            .should().onlyHaveDependentClassesThat().resideInAnyPackage("..repository..",
                    "..service..", "..mapper..", "..usecase..");

    @ArchTest
    static ArchRule resources_should_depend_only_on_usecase =
            classes()
                    .that().resideInAPackage("..resource..")
                    .should().onlyHaveDependentClassesThat().resideInAnyPackage("..usecase..");
}


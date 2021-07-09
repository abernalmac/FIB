package FONTS.Domain;

import FONTS.Persistence.PersistenceController;
import FONTS.Presentation.PresentationController;

public class Othello {
    public static DomainController domain;
    public static PresentationController presentation;
    public static PersistenceController persistence;

    public static void main(String[] args) {
        presentation = new PresentationController();
        persistence = new PersistenceController();
        domain = new DomainController(persistence, presentation);
        persistence.linkDomain(domain);
        presentation.linkDomain(domain);
        domain.start();
    }
}

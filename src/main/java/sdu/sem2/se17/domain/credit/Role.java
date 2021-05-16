package sdu.sem2.se17.domain.credit;


import java.util.Locale;

/* Mikkel Nielsen
Casper Fenger Jensen */
public enum Role {
    BILLEDKUNSTNERE("Billedkunstnere"),
    BILLEDREDIGERING("Billedredigering"),
    LYDREDIGERING("Lydredigering"),
    CASTING("Casting"),
    COLOURGRADING("Colourgrading"),
    DIRIGENTER("Dirigenter"),
    DRONE("Drone"),
    PUPPETRY("PUPPETRY"),
    DUKKESKABER("Dukkeskaber"),
    NARRATOR("Narrator"),
    FOTOGRAFER("Fotografer"),
    PUBLISHER("PUBLISHER"),
    GRAFISKE_DESIGNERE("Grafiske Designere"),
    INDTALERE("Indetalere"),
    KAPELMESTER("Kapelmester"),
    KLIPPER("Klipper"),
    KONCEPT("Koncept"),
    KONSULENT("Konsulent"),
    KOR("Kor"),
    KOREOGRAFI("Koregrafi"),
    LYDMESTER("Lydmester"),
    LYS("Lys"),
    MEDVIRKENDE("Medvirkende"),
    MUSIKERE("Musikere"),
    MUSIKALSK_ARRANGEMENT("Musikalsk_Arrangement"),
    BAND("Band"),
    TRANSLATORS("Translator"),
    PRODUCENT("Producent"),
    PRODUCER("Producer"),
    PRODUKTIONSKOORDINATOR_EL_PRODUKTIONSLEDER("Produktionskoordinator el produktionsleder"),
    PROGRAMANSVARLIG("Programansvarlig"),
    REDAKTION("Redaktion"),
    EDITOR("EDITOR"),
    PROBS("Probs"),
    SCENOGRAFER("SCENEGRAFER"),
    SCRIPTER("Scripter"),
    SPECIAL_EFFECTS("Special_Effects"),
    SPONSORE("Sponsore"),
    ANIMATION("Animation"),
    TEkSTERE("Tekstere"),
    TEKST_OG_MUSIK("Tekst_og_musik"),
    UNPAID_AND_EXTRAORDINARY_EFFORT("Unpaid_and_extraordinary_effort");

    final String label;

    Role(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public static Role getRole(String label) {
        for (Role i: Role.values()) {
            if (i.label.equalsIgnoreCase(label)) {return i;}
        }
        return null;
    }
}
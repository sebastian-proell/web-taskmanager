package de.sp.taskmanager.model;

import java.util.Objects;

/**
 * Abstrakte Basisklasse für alle Entitäten.
 *
 * Diese Klasse stellt eine eindeutige ID sowie sinnvolle Implementierungen
 * von equals() und hashCode() bereit. Dadurch müssen diese Methoden nicht
 * in jeder Entity-Klasse wiederholt werden.
 */
public abstract class AbstractEntity {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Zwei Entitäten sind gleich, wenn sie dieselbe ID haben.
     * Wichtig für Datenbank-Entitäten
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

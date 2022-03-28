/*
 * Copyright 2022 IQTIG – Institut für Qualitätssicherung und Transparenz im Gesundheitswesen.
 * Dieser Code ist urheberrechtlich geschützt (Copyright). Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet, beim IQTIG.
 * Wer gegen das Urheberrecht verstößt, macht sich gem. § 106 ff Urhebergesetz strafbar. Er wird zudem kostenpflichtig abgemahnt und muss
 * Schadensersatz leisten.
 */
package de.koelle.christian.wicket.demo.shared.client;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;
import org.apache.wicket.util.io.IClusterable;
import de.koelle.christian.wicket.demo.shared.common.Preconditions;

public class SingleSelectionWidgetMO<T extends Serializable> implements IClusterable {

    public static final String PROP_NAME_SELECTION = "selection";
    public static final String PROP_NAME_SELECTABLES = "selectables";
    public static final String PROP_NAME_VISIBLE = "visible";
    public static final String PROP_NAME_ACTIVE = "active";

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // DataModel
    private T selection;
    private List<T> selectables;
    // StateModel
    private Boolean visible = Boolean.TRUE;
    private Boolean active = Boolean.TRUE;


    public T getSelection() {
        return selection;
    }

    public List<T> getSelectables() {
        return selectables;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Boolean isActive() {
        return active;
    }

    public void setSelection(final T selectionIn) {
        Preconditions.checkArgument(selectionIn == null || selectables != null && selectables.contains(selectionIn));

        T oldValue = this.selection;
        this.selection = selectionIn;
        pcs.firePropertyChange(PROP_NAME_SELECTION, oldValue, selectionIn);
    }

    public SingleSelectionWidgetMO<T> setSelectables( final List<T> selectablesIn, final T selectionIn) {
        Preconditions.checkArgument(selectablesIn != null && (selectionIn == null ||  selectablesIn.contains(selectionIn)));

        List<T> oldValue = this.selectables;
        T oldValueOther = this.selection;
        this.selectables = selectablesIn;
        this.selection = selectionIn;
        pcs.firePropertyChange(PROP_NAME_SELECTABLES, oldValue, selectablesIn);
        pcs.firePropertyChange(PROP_NAME_SELECTION, oldValueOther, selectionIn);
        return this;
    }

    public SingleSelectionWidgetMO<T> setVisible(final Boolean visibleIn) {
        Boolean oldValue = this.visible;
        this.visible = visibleIn;
        pcs.firePropertyChange(PROP_NAME_VISIBLE, oldValue, visibleIn);
        return this;
    }

    public SingleSelectionWidgetMO<T> setActive(final Boolean activeIn) {
        Boolean oldValue = this.active;
        this.active = activeIn;
        pcs.firePropertyChange(PROP_NAME_ACTIVE, oldValue, activeIn);
        return this;
    }

    public void addPcl(String propertyName, PropertyChangeListener listener){
        this.pcs.addPropertyChangeListener(propertyName, listener);
    }
    public void removePcl(String propertyName, PropertyChangeListener listener){
        this.pcs.removePropertyChangeListener(propertyName, listener);
    }
}

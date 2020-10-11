/*
 * Copyright 2020 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.cuba.web.gui.components;

import com.haulmont.cuba.gui.components.RowsCount;
import com.haulmont.cuba.gui.components.TreeTable;
import com.haulmont.cuba.gui.components.data.table.AggregatableTableItems;
import com.haulmont.cuba.gui.presentation.LegacyPresentationsDelegate;
import com.haulmont.cuba.gui.presentation.Presentations;
import com.haulmont.cuba.settings.binder.CubaTreeTableSettingsBinder;
import com.haulmont.cuba.settings.component.LegacySettingsDelegate;
import com.haulmont.cuba.settings.converter.LegacyTableSettingsConverter;
import com.haulmont.cuba.web.gui.components.table.TableDelegate;
import io.jmix.core.Entity;
import io.jmix.ui.component.AggregationInfo;
import io.jmix.ui.component.data.TableItems;
import io.jmix.ui.component.presentation.TablePresentationsLayout;
import io.jmix.ui.presentation.TablePresentations;
import io.jmix.ui.presentation.model.TablePresentation;
import io.jmix.ui.settings.component.binder.ComponentSettingsBinder;
import io.jmix.ui.widget.data.AggregationContainer;
import org.dom4j.Element;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Deprecated
public class WebTreeTable<E extends Entity> extends io.jmix.ui.component.impl.WebTreeTable<E> implements TreeTable<E> {

    protected LegacySettingsDelegate settingsDelegate;
    protected LegacyPresentationsDelegate presentationsDelegate;
    protected TableDelegate tableDelegate;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        settingsDelegate = createSettingsDelegate();
        tableDelegate = createTableDelegate();
    }

    @Override
    public void applyDataLoadingSettings(Element element) {
        settingsDelegate.applyDataLoadingSettings(element);
    }

    @Override
    public void applySettings(Element element) {
        settingsDelegate.applySettings(element);
    }

    @Override
    public boolean saveSettings(Element element) {
        return settingsDelegate.saveSettings(element);
    }

    @Override
    public boolean isSettingsEnabled() {
        return settingsDelegate.isSettingsEnabled();
    }

    @Override
    public void setSettingsEnabled(boolean settingsEnabled) {
        settingsDelegate.setSettingsEnabled(settingsEnabled);
    }

    @Override
    protected ComponentSettingsBinder getSettingsBinder() {
        return (ComponentSettingsBinder) applicationContext.getBean(CubaTreeTableSettingsBinder.NAME);
    }

    protected LegacySettingsDelegate createSettingsDelegate() {
        return (LegacySettingsDelegate) applicationContext.getBean(LegacySettingsDelegate.NAME,
                this, new LegacyTableSettingsConverter(), getSettingsBinder());
    }

    @Override
    protected TablePresentations createTablePresentations() {
        Presentations presentations = applicationContext.getBean(Presentations.class, this);

        presentationsDelegate = applicationContext.getBean(LegacyPresentationsDelegate.class,
                this, presentations, getSettingsBinder());

        return presentations;
    }

    @Override
    protected TablePresentationsLayout createTablePresentationsLayout() {
        TablePresentationsLayout layout = super.createTablePresentationsLayout();
        return presentationsDelegate.createTablePresentationsLayout(layout);
    }

    @Override
    protected void updatePresentationSettings(TablePresentations p) {
        if (settingsDelegate.isLegacySettings(getFrame())) {
            presentationsDelegate.updatePresentationSettings((Presentations) p);
        } else {
            super.updatePresentationSettings(p);
        }
    }

    @Override
    protected void applyPresentationSettings(TablePresentation p) {
        if (settingsDelegate.isLegacySettings(getFrame())) {
            presentationsDelegate.applyPresentationSettings(p);
        } else {
            super.applyPresentationSettings(p);
        }
    }

    @Override
    public void resetPresentation() {
        if (settingsDelegate.isLegacySettings(getFrame())) {
            presentationsDelegate.resetPresentations(settingsDelegate.getDefaultSettings());
        } else {
            super.resetPresentation();
        }
    }

    @Override
    public void setItems(@Nullable TableItems<E> tableItems) {
        super.setItems(tableItems);

        if (tableItems != null) {
            if (getRowsCount() != null) {
                getRowsCount().setRowsCountTarget(this);
            }
        }
    }

    @Nullable
    @Override
    public RowsCount getRowsCount() {
        return tableDelegate.getRowsCount();
    }

    @Override
    public void setRowsCount(@Nullable RowsCount rowsCount) {
        tableDelegate.setRowsCount(rowsCount, topPanel, this::createTopPanel, componentComposition,
                this::updateCompositionStylesTopPanelVisible);
    }

    protected TableDelegate createTableDelegate() {
        return (TableDelegate) applicationContext.getBean(TableDelegate.NAME);
    }

    @Override
    protected Map<Object, Object> __aggregateValues(AggregationContainer container, AggregationContainer.Context context) {
        if (getItems() instanceof AggregatableTableItems) {
            List<AggregationInfo> aggregationInfos = getAggregationInfos(container);

            Map<AggregationInfo, Object> results = ((AggregatableTableItems<E>) getItems()).aggregateValues(
                    aggregationInfos.toArray(new AggregationInfo[0]),
                    context.getItemIds()
            );

            return convertAggregationKeyMapToColumnIdKeyMap(container, results);
        } else {
            return super.__aggregateValues(container, context);
        }
    }

    @Override
    protected Map<Object, Object> __aggregate(AggregationContainer container, AggregationContainer.Context context) {
        if (getItems() instanceof AggregatableTableItems) {
            List<AggregationInfo> aggregationInfos = getAggregationInfos(container);

            Map<AggregationInfo, String> results = ((AggregatableTableItems<E>) getItems()).aggregate(
                    aggregationInfos.toArray(new AggregationInfo[0]),
                    context.getItemIds()
            );

            Map<Object, Object> resultsByColumns = convertAggregationKeyMapToColumnIdKeyMap(container, results);

            if (aggregationCells != null) {
                resultsByColumns = __handleAggregationResults(context, resultsByColumns);
            }
            return resultsByColumns;
        } else {
            return super.__aggregate(container, context);
        }
    }
}

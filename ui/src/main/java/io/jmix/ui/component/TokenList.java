/*
 * Copyright 2019 Haulmont.
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

package io.jmix.ui.component;

import io.jmix.core.JmixEntity;
import io.jmix.ui.Screens;
import io.jmix.ui.component.data.Options;
import io.jmix.ui.component.data.options.ListEntityOptions;
import io.jmix.ui.component.data.options.MapEntityOptions;
import io.jmix.ui.screen.LookupScreen;
import io.jmix.ui.screen.OpenMode;
import io.jmix.ui.screen.Screen;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public interface TokenList<V extends JmixEntity> extends Field<Collection<V>>,
        Component.BelongToFrame, Component.HasCaption, Component.Editable, Component.Focusable,
        HasOptionCaptionProvider<V> {

    String NAME = "tokenList";

    /**
     * Sets field options.
     *
     * @param options field options
     */
    void setOptions(@Nullable Options<V> options);

    /**
     * @return field options
     */
    @Nullable
    Options<V> getOptions();

    /**
     * @return options filter mode
     */
    ComboBox.FilterMode getFilterMode();

    /**
     * Sets the given {@code mode} to manage how options should be filtered.
     *
     * @param mode options filter mode
     */
    void setFilterMode(ComboBox.FilterMode mode);

    /**
     * Sets function that provides caption for LookupField options.
     *
     * @param optionsCaptionProvider caption provider for options
     */
    void setLookupFieldOptionsCaptionProvider(@Nullable Function<? super V, String> optionsCaptionProvider);

    /**
     * @return caption provider for LookupField options
     */
    @Nullable
    Function<? super V, String> getLookupFieldOptionsCaptionProvider();

    /**
     * Sets whether options should be refreshed after lookup window closing.
     */
    void setRefreshOptionsOnLookupClose(boolean refresh);

    /**
     * @return whether options should be refreshed after lookup window closing
     */
    boolean isRefreshOptionsOnLookupClose();

    /**
     * @return options list
     *
     * @deprecated use {@link TokenList#getOptions()} instead
     */
    @SuppressWarnings("rawtypes")
    @Deprecated
    @Nullable
    default List getOptionsList() {
        Options<V> options = getOptions();
        if (options instanceof ListEntityOptions) {
            return ((ListEntityOptions<V>) options).getItemsCollection();
        }
        return null;
    }

    /**
     * @param optionsList options list
     */
    void setOptionsList(List optionsList);

    /**
     * @return options map
     * @deprecated use {@link TokenList#getOptions()} instead
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Deprecated
    @Nullable
    default Map<String, ?> getOptionsMap() {
        Options options = getOptions();
        if (options instanceof MapEntityOptions) {
            return ((MapEntityOptions) options).getItemsCollection();
        }
        return null;
    }

    /**
     * @param optionsMap options map
     */
    void setOptionsMap(Map<String, ?> optionsMap);

    /**
     * @return whether inner LookupPickerField component has lookup action
     */
    boolean isLookup();

    /**
     * Sets whether inner LookupPickerField component should have lookup action.
     *
     * @param lookup enable lookup action
     */
    void setLookup(boolean lookup);

    /**
     * Sets a lookup screen provider that is used when {@link TokenList#isLookup()} enabled.
     * <p>
     * Provided screen should implement {@link LookupScreen} interface.
     *
     * @param lookupProvider lookup screen provider
     */
    void setLookupProvider(@Nullable Supplier<Screen> lookupProvider);

    /**
     * @return lookup screen provider
     */
    @Nullable
    Supplier<Screen> getLookupProvider();

    /**
     * @return lookup screen alias
     */
    @Deprecated
    @Nullable
    String getLookupScreen();

    /**
     * Sets lookup screen alias.
     *
     * @param lookupScreen screen alias
     *
     * @deprecated use {@link TokenList#setLookupProvider(Supplier)} instead
     */
    @Deprecated
    void setLookupScreen(@Nullable String lookupScreen);

    /**
     * Sets params that will be passed to lookup screen.
     *
     * @param params params
     *
     * @deprecated use {@link TokenList#setLookupProvider(Supplier)} instead
     */
    @Deprecated
    void setLookupScreenParams(@Nullable Map<String, Object> params);

    /**
     * @return params that will be passed to lookup screen
     */
    @Nullable
    @Deprecated
    Map<String, Object> getLookupScreenParams();

    /**
     * @return launch mode for Lookup screen
     * @see #getLookupProvider()
     */
    Screens.LaunchMode getLookupLaunchMode();

    /**
     * Sets launch mode for Lookup screen. Uses {@link OpenMode#THIS_TAB} if not set.
     *
     * @param launchMode screen open mode
     * @see #setLookupProvider(Supplier)
     */
    void setLookupLaunchMode(Screens.LaunchMode launchMode);

    /**
     * @return clear button is enabled
     */
    boolean isClearEnabled();

    /**
     * Sets whether clear button is enabled or not
     *
     * @param clearEnabled clear button enabled
     */
    void setClearEnabled(boolean clearEnabled);

    /**
     * @return whether multiselect mode is enabled
     */
    boolean isMultiSelect();

    /**
     * Enables multiselect mode. It leads to the passing {@code MULTI_SELECT} param
     * to the lookup screen.
     *
     * @param multiselect multiselect
     */
    void setMultiSelect(boolean multiselect);

    /**
     * @return whether simple mode is used ("Add button" instead of LookupPickerField)
     */
    boolean isSimple();

    /**
     * Sets whether simple mode should be used ("Add button" instead of LookupPickerField)
     *
     * @param simple simple
     */
    void setSimple(boolean simple);

    /**
     * @return component editor (LookupPickerField / "Add" button) position
     */
    Position getPosition();

    /**
     * Sets component editor (LookupPickerField / "Add" button) position.
     * <p>
     * {@link Position#TOP} is the default.
     *
     * @param position editor position
     */
    void setPosition(Position position);

    /**
     * @return whether inline tokens mode should be used
     */
    boolean isInline();

    /**
     * Sets whether inline tokens mode should be used.
     *
     * @param inline inline mode
     */
    void setInline(boolean inline);

    /**
     * @return "Add" button caption
     */
    @Nullable
    String getAddButtonCaption();

    /**
     * Sets "Add" button caption.
     *
     * @param caption caption
     */
    void setAddButtonCaption(@Nullable String caption);

    /**
     * @return "Add" button icon
     */
    @Nullable
    String getAddButtonIcon();

    /**
     * Sets "Add" button icon.
     *
     * @param icon icon
     */
    void setAddButtonIcon(@Nullable String icon);

    /**
     * @return "Clear" button caption
     */
    @Nullable
    String getClearButtonCaption();

    /**
     * Sets "Clear" button caption.
     *
     * @param caption caption
     */
    void setClearButtonCaption(@Nullable String caption);

    /**
     * @return "Clear" button icon
     */
    @Nullable
    String getClearButtonIcon();

    /**
     * Sets "Clear" button icon.
     *
     * @param icon icon
     */
    void setClearButtonIcon(@Nullable String icon);

    /**
     * @return selected items change handler
     */
    @Nullable
    ItemChangeHandler getItemChangeHandler();

    /**
     * Sets selected items change handler.
     *
     * @param handler items change handler
     */
    void setItemChangeHandler(@Nullable ItemChangeHandler handler);

    /**
     * @return selected tokens click listener
     */
    @Nullable
    ItemClickListener getItemClickListener();

    /**
     * Sets selected tokens click listener.
     *
     * @param itemClickListener items click listener
     */
    void setItemClickListener(@Nullable ItemClickListener itemClickListener);

    /**
     * @return handler that is invoked after lookup screen closing
     *
     * @deprecated use {@link TokenList#setLookupProvider(Supplier)} instead
     */
    @Nullable
    @Deprecated
    AfterLookupCloseHandler getAfterLookupCloseHandler();

    /**
     * Sets handler that is invoked after lookup screen closing.
     *
     * @param handler handler
     *
     * @deprecated use {@link TokenList#setLookupProvider(Supplier)} instead
     */
    @Deprecated
    void setAfterLookupCloseHandler(@Nullable AfterLookupCloseHandler handler);

    /**
     * @return handler that is invoked when an item is selected in lookup screen
     *
     * @deprecated use {@link TokenList#setLookupProvider(Supplier)} instead
     */
    @Nullable
    @Deprecated
    AfterLookupSelectionHandler getAfterLookupSelectionHandler();

    /**
     * Sets handler that is invoked when an item is selected in lookup screen.
     *
     * @param handler handler
     *
     * @deprecated use {@link TokenList#setLookupProvider(Supplier)} instead
     */
    @Deprecated
    void setAfterLookupSelectionHandler(@Nullable AfterLookupSelectionHandler handler);

    @Deprecated
    void setTokenStyleGenerator(@Nullable Function<Object, String> tokenStyleGenerator);

    @Nullable
    @Deprecated
    Function<Object, String> getTokenStyleGenerator();

    /**
     * @return input prompt of LookupPickerField
     */
    @Nullable
    String getLookupInputPrompt();

    /**
     * Sets the input prompt - a textual prompt that is displayed when the LookupPickerField
     * would otherwise be empty, to prompt the user for input.
     *
     * @param inputPrompt input prompt
     */
    void setLookupInputPrompt(@Nullable String inputPrompt);

    /**
     * Enables to generate stylenames for tokens.
     *
     * @deprecated
     */
    @Deprecated
    interface TokenStyleGenerator extends Function<Object, String> {

        @Override
        default String apply(Object itemId) {
            return getStyle(itemId);
        }

        String getStyle(Object itemId);
    }

    /**
     * Enables to handle selected items change.
     */
    interface ItemChangeHandler {

        /**
         * Invoked when item is added.
         *
         * @param item item
         */
        void addItem(Object item);

        /**
         * Invoked when item is removed.
         *
         * @param item item
         */
        void removeItem(Object item);
    }

    /**
     * Selected items click handler.
     */
    @FunctionalInterface
    interface ItemClickListener {

        /**
         * Invoked when selected item is clicked.
         *
         * @param item item
         */
        void onClick(Object item);
    }

    /**
     * Enables to handle lookup screen closing.
     *
     * @deprecated use {@link TokenList#setLookupProvider(Supplier)} instead
     */
    @Deprecated
    @FunctionalInterface
    interface AfterLookupCloseHandler {

        /**
         * Invoked when lookup screen is closed.
         *
         * @param window   window
         * @param actionId action id
         */
        void onClose(Window window, String actionId);
    }

    /**
     * Enables to handle item selection in lookup screen.
     *
     * @deprecated use {@link TokenList#setLookupProvider(Supplier)} instead
     */
    @Deprecated
    @FunctionalInterface
    interface AfterLookupSelectionHandler {

        /**
         * Invoked when items are selected.
         *
         * @param items items
         */
        void onSelect(Collection items);
    }

    /**
     * Defines component editor position.
     */
    enum Position {

        /**
         * Editor is above tokens container.
         */
        TOP,

        /**
         * Editor is under tokens container.
         */
        BOTTOM
    }
}

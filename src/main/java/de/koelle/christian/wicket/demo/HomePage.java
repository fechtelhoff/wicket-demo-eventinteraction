package de.koelle.christian.wicket.demo;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.koelle.christian.wicket.demo.shared.client.SingleSelectionWidgetMO;
import de.koelle.christian.wicket.demo.shared.common.A;
import de.koelle.christian.wicket.demo.shared.common.B;
import de.koelle.christian.wicket.demo.shared.common.C;

@SuppressWarnings("java:S110") // S110: Inheritance tree of classes should not be too deep
public class HomePage extends WebPage {

	private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);

	private final HomePageSelectionMO homePageSelectionMO = new HomePageSelectionMO();
	private final SingleSelectionWidgetMO<A> selectionModelA = new SingleSelectionWidgetMO<>();
	private final SingleSelectionWidgetMO<B> selectionModelB = new SingleSelectionWidgetMO<>();
	private final SingleSelectionWidgetMO<C> selectionModelC = new SingleSelectionWidgetMO<>();

	private HomePageService service = new HomePageService();

	public HomePage(final PageParameters parameters) {
		super(parameters);
		selectionModelB.setVisible(Boolean.TRUE);
		selectionModelB.setVisible(Boolean.FALSE);
		selectionModelC.setVisible(Boolean.FALSE);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		WebMarkupContainer panel = new WebMarkupContainer("mainpanel");
		panel.setOutputMarkupId(true);
		panel.add(new Label("selectionModelOutput", () -> service.getStringRepresentation()));
		panel.add(new Label("selectionA", homePageSelectionMO::a));
		panel.add(new Label("selectionB", homePageSelectionMO::b));
		panel.add(new Label("selectionC", homePageSelectionMO::c));
		panel.add(createBoundAjaxifiedDropDownChoice("choiceA", selectionModelA, panel));
		panel.add(createBoundAjaxifiedDropDownChoice("choiceB", selectionModelB, panel));
		panel.add(createBoundAjaxifiedDropDownChoice("choiceC", selectionModelC, panel));

		Form<Void> form = new Form<>("form");
		form.add(new AjaxButton("buttonA") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				super.onSubmit(target);
				setModelAValue(getRandomAValue());
				target.add(panel);
			}
		});
		panel.add(form);
		add(panel);
		onIntializeBindModelListeners();
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		setModelAValue(A.A1);
	}

	private <T extends Serializable> DropDownChoice<T> createBoundAjaxifiedDropDownChoice(final String widgetId, SingleSelectionWidgetMO<T> modelToBind, Component targetComponent) {
		final DropDownChoice<T> result = new DropDownChoice<>(widgetId,
			LambdaModel.of(modelToBind::getSelection, modelToBind::setSelection),
			LambdaModel.of(modelToBind::getSelectables)
		) {
			@Override
			public boolean isVisible() {
				return modelToBind.isVisible();
			}

			@Override
			protected void onBeforeRender() {
				super.onBeforeRender();
				setEnabled(modelToBind.isActive());
			}
		};
		result.add(new AjaxFormComponentUpdatingBehavior("change") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(targetComponent);
			}
		});
		return result;
	}

	private void setModelAValue(final A value) {
		selectionModelA.setSelectables(Arrays.asList(A.values()), value);
	}

	private void onIntializeBindModelListeners() {
		selectionModelA.addPcl(SingleSelectionWidgetMO.PROP_NAME_SELECTION, e -> {
			logPce(e);
			homePageSelectionMO.setA(selectionModelA.getSelection());
			loadNextSelectionAndUpdateSelectionModel(e, selectionModelA, selectionModelB, i -> service.getOrangesForForAnApple(i));
		});
		selectionModelB.addPcl(SingleSelectionWidgetMO.PROP_NAME_SELECTION, e -> {
			logPce(e);
			homePageSelectionMO.setB(selectionModelB.getSelection());
			loadNextSelectionAndUpdateSelectionModel(e, selectionModelB, selectionModelC, i -> service.getOrangesForForAnApple(i));
		});
		selectionModelC.addPcl(SingleSelectionWidgetMO.PROP_NAME_SELECTION, event -> {
			logPce(event);
			homePageSelectionMO.setC(selectionModelC.getSelection());
		});
	}

	private <I extends Serializable, J extends Serializable> void loadNextSelectionAndUpdateSelectionModel(final PropertyChangeEvent e, final SingleSelectionWidgetMO<I> sourceSelectionModel, final SingleSelectionWidgetMO<J> targetSelectionModel, final Function<I, List<J>> valueResolver) {
		Boolean isToBeVisible = Boolean.FALSE;
		J selectionIn = null;
		if (e.getNewValue() != null) {
			final List<J> orangesForForAnApple = valueResolver.apply(sourceSelectionModel.getSelection());
			selectionIn = orangesForForAnApple.size() == 1 ?
				orangesForForAnApple.get(0) :
				null;
			targetSelectionModel.setSelectables(orangesForForAnApple, selectionIn);
			isToBeVisible = !orangesForForAnApple.isEmpty();
		}
		targetSelectionModel.setSelection(selectionIn);
		targetSelectionModel.setVisible(isToBeVisible);
	}

	private void logPce(PropertyChangeEvent event) {
		LOG.info("PCE name={} old={} new={}", event.getPropertyName(), event.getOldValue(), event.getNewValue());
	}

	private A getRandomAValue() {
		return A.values()[ThreadLocalRandom.current().nextInt(0, A.values().length)];
	}
}

package com.urbanlaunchpad.newmarket;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.urbanlaunchpad.newmarket.helpers.typefaceHelper;
import com.urbanlaunchpad.newmarket.model.Run;
import com.urbanlaunchpad.newmarket.model.RunsClient;
import com.urbanlaunchpad.newmarket.model.StepsClient;

public class RunDialogFragment extends UlDialogFragment {
	private static final String TAG_NAME = "addRunDialog";

	private RunCreationListener runCreationListener;

	/**
	 * Create a new instance of RunDialogFragment.
	 */
	static RunDialogFragment newInstance() {
		return new RunDialogFragment();
	}

	public void setRunCreationListener(RunCreationListener runCreationListener) {
		this.runCreationListener = runCreationListener;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int style = DialogFragment.STYLE_NORMAL;
		int theme = android.R.style.Theme_Holo_Light_Dialog;
		setStyle(style, theme);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_run, container, false);

		final Spinner spTextile = getAndInitSpinner(v);

		// When save button is clicked, create a run with the run info,
		// callback the runs activity with the new run
		// dismiss the dialog.

		Button button = (Button) typefaceHelper.setCustomTypeface(
				v.findViewById(R.id.btnSave), getActivity());
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (runCreationListener != null) {
					runCreationListener.onRunCreated(getNewRun(spTextile));
				}
				dismiss();
			}
		});

		return v;
	}

	/**
	 * Initializes the spinner and returns it.
	 */
	private Spinner getAndInitSpinner(View v) {
		Spinner spTextile = (Spinner) v.findViewById(R.id.spTextile);
		List<String> textileOptions = RunsClient.getInstance()
				.getTextileOptions();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, textileOptions);
		spTextile.setAdapter(adapter);

		return spTextile;
	}

	/**
	 * Creates a new run with the information in spTextile.
	 * 
	 * @param spTextile
	 */
	private Run getNewRun(Spinner spTextile) {
		String startStep = StepsClient.getInstance().getStart();
		String textile = spTextile.getSelectedItem().toString();
		Date time_last_update_UTC = new Date();
		return new Run(textile, 1, startStep, time_last_update_UTC);
	}

	/**
	 * Return the tag name of the matching xml file. This is the value in
	 * android:tag of the top level xml layout.
	 */
	public static String getTagName() {
		return TAG_NAME;
	}
}

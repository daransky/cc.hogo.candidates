package com.hogo.portal;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.hogo.portal.candidate.ui.CustomAction;
import com.hogo.portal.candidates.CandidateView;
import com.hogo.portal.settings.SettingsView;
import com.hogo.portal.skills.SkillsView;
import com.hogo.portal.statistics.StatisticsView;
import com.hogo.portal.views.log.LogView;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// Actions - important to allocate these only in makeActions, and then use them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction saveEditAction;
	private IWorkbenchAction saveEditAllAction;
	private OpenView openLogView;
	private OpenView openCandidateView;
	private OpenView openStatisticView;
	private OpenView openSettingsView;
	private OpenView openSkillsView;
	private OpenCandidateEditor newCandidateAction;
	private CustomAction export;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(final IWorkbenchWindow window) {

		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);

		newCandidateAction = new OpenCandidateEditor(window);
		register(newCandidateAction);

		openCandidateView = new OpenView("Kandidaten", CandidateView.ID, Activator.getImageDescriptor("icons/candidates.png"), "Candidates.open");
		register(openCandidateView);
		openStatisticView= new OpenView("Statistiken", StatisticsView.ID, Activator.getImageDescriptor("icons/info_tsk.png"), "Candidates.open");
		register(openStatisticView);
		openLogView = new OpenView("Log", LogView.ID, Activator.getImageDescriptor("icons/log.png"), "Log.open");
		register(openLogView);
		openSettingsView = new OpenView("Einstellungen", SettingsView.ID, Activator.getImageDescriptor("icons/settings.png"), "Settings.open");
		register(openSettingsView);
		openSkillsView = new OpenView("Qualifikationen", SkillsView.ID, Activator.getImageDescriptor("icons/skills.gif"), "Skills.open");
		register(openSkillsView);

		saveEditAction = ActionFactory.SAVE.create(window);
		register(saveEditAction);

		saveEditAllAction = ActionFactory.SAVE_ALL.create(window);
		register(saveEditAllAction);

		export = new ClipboardExportAction();
		register(export);
	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);

		menuBar.add(fileMenu);
		// Add a group marker indicating where action set menus will appear.
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(helpMenu);

		// File
		fileMenu.add(newCandidateAction);
		fileMenu.add(openCandidateView);
		fileMenu.add(openSkillsView);
		fileMenu.add(openLogView);
		fileMenu.add(openSettingsView);
		fileMenu.add(openStatisticView);
		fileMenu.add(new Separator());
		fileMenu.add(saveEditAction);
		fileMenu.add(saveEditAllAction);
		fileMenu.add(new Separator());
		fileMenu.add(exitAction);

		// Help
		helpMenu.add(aboutAction);
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar, "main"));
		toolbar.add(newCandidateAction);
		toolbar.add(openCandidateView);
		toolbar.add(saveEditAction);
		toolbar.add(saveEditAllAction);
		toolbar.add(export);
	}
}

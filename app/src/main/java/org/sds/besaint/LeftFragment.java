package org.sds.besaint;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.feras.mdv.MarkdownView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment {


    public LeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            MarkdownView markdownView = (MarkdownView) view.findViewById(R.id.id_leftFragment_markdownView);

            // Find day in the DB


            markdownView.loadMarkdown("##John 1:1 Na początku było Słowo, a Słowo było u Boga, i Bogiem było Słowo.\n" +
                    " 2 Ono było na początku u Boga.\n" +
                    " 3 Wszystko przez Nie się stało, a bez Niego nic się nie stało, co się stało.\n" +
                    " 4 W Nim było życie, a życie było światłością ludzi,\n" +
                    " 5 a światłość w ciemności świeci i ciemność jej nie ogarnęła.\n" +
                    " 6 Pojawił się człowiek posłany przez Boga - Jan mu było na imię.\n" +
                    " 7 Przyszedł on na świadectwo, aby zaświadczyć o światłości, by wszyscy uwierzyli przez niego.\n" +
                    " 8 Nie był on światłością, lecz posłanym, aby zaświadczyć o światłości.\n" +
                    " 9 Była światłość prawdziwa, która oświeca każdego człowieka, gdy na świat przychodzi.\n" +
                    " 10 Na świecie było Słowo, a świat stał się przez Nie, lecz świat Go nie poznał.\n" +
                    " 11 Przyszło do swojej własności, a swoi Go nie przyjęli.\n" +
                    " 12 Wszystkim tym jednak, którzy Je przyjęli, dało moc, aby się stali dziećmi Bożymi, tym, którzy wierzą w imię Jego -\n" +
                    " 13 którzy ani z krwi, ani z żądzy ciała, ani z woli męża, ale z Boga się narodzili.\n" +
                    " 14 A Słowo stało się ciałem i zamieszkało wśród nas. I oglądaliśmy Jego chwałę, chwałę, jaką Jednorodzony otrzymuje od Ojca, pełen łaski i prawdy.\n" +
                    " 15 Jan daje o Nim świadectwo i głośno woła w słowach: «Ten był, o którym powiedziałem: Ten, który po mnie idzie, przewyższył mnie godnością, gdyż był wcześniej ode mnie».\n" +
                    " 16 Z Jego pełności wszyscyśmy otrzymali - łaskę po łasce.\n" +
                    " 17 Podczas gdy Prawo zostało nadane przez Mojżesza, łaska i prawda przyszły przez Jezusa Chrystusa.\n" +
                    " 18 Boga nikt nigdy nie widział, Ten Jednorodzony Bóg, który jest w łonie Ojca, o Nim pouczył.\n" +
                    " (Joh 1:1-18 BTP)");
        }
    }
}

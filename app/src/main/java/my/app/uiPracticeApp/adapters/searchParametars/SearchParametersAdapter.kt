package my.app.uiPracticeApp.adapters.searchParametars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.app.uiPracticeApp.databinding.ItemSearchParametarBinding

class SearchParametersAdapter : RecyclerView.Adapter<ItemSearchParametersViewHolder>() {

    private var mParameterList = ArrayList<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemSearchParametersViewHolder {
        val mBinding =
            ItemSearchParametarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSearchParametersViewHolder(
            mBinding
        )
    }

    override fun getItemCount(): Int = mParameterList.count()

    override fun onBindViewHolder(holder: ItemSearchParametersViewHolder, position: Int) {
        holder.setDate(mParameterList.get(position))
    }

    fun setData(parameterList: List<String>) {
        this.mParameterList = ArrayList(parameterList)
        notifyDataSetChanged()
    }
}

class ItemSearchParametersViewHolder(private val mBinding: ItemSearchParametarBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun setDate(parameter: String) {
        mBinding.parameterMeasure.text = parameter
    }
}

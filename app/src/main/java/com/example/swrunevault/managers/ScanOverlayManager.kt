package com.example.swrunevault.managers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.swrunevault.R
import com.example.swrunevault.controls.DividerView
import com.example.swrunevault.controls.LineOrientation
import com.example.swrunevault.controls.ProgressStatView
import com.example.swrunevault.controls.StatView
import com.example.swrunevault.controls.SubStatView
import com.example.swrunevault.controls.UiFactory
import com.example.swrunevault.extensions.colorRes
import com.example.swrunevault.models.Rune
import com.example.swrunevault.models.RuneStatType
import com.example.swrunevault.utils.getStars
import com.example.swrunevault.views.scanoverlay.createScanOverlayContainer

class ScanOverlayManager(
    private val context: Context
) {

    private val windowManager =
        context.getSystemService(
            Context.WINDOW_SERVICE
        ) as WindowManager

    private var overlayView: FrameLayout = FrameLayout(context).apply {
        setBackgroundColor(
            context.colorRes(R.color.background_primary)
        )
    }

    fun show(
        rune: Rune,
        onClose: () -> Unit
    ) {
        remove()

        // Fondo fullscreen
       /* overlayView =
            FrameLayout(context).apply {
                setBackgroundColor(
                    context.colorRes(R.color.background_primary)
                )
            }*/

        val container =
            createScanOverlayContainer(
                context
            )

        container.addView(
            createScanOverlayInformation(
                context,
                rune,
                onClose,
                ::remove
            )
        )

        container.addView(
            createScanOverlayRightPanel(
                context,
                rune
            )
        )

        overlayView?.addView(
            container
        )

        val params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            )

        windowManager.addView(
            overlayView,
            params
        )
    }

    fun remove() {
        overlayView.let {
            if (it.parent != null) {
                windowManager.removeView(it)
            }
        }
        //overlayView = null
    }

    private var baseCard: ProgressStatView? = null
    private var actualCard: ProgressStatView? = null
    private var maxCard: ProgressStatView? = null

    private var editMode: Boolean = false

    @SuppressLint("SetTextI18n")
    fun createScanOverlayInformation(
        context: Context,
        rune: Rune,
        onClose: () -> Unit,
        onRemove: () -> Unit
    ): FrameLayout {
        // FUNCIÓN AUXILIAR: Convierte valores DP a Píxeles reales según la pantalla del dispositivo
        val density = context.resources.displayMetrics.density
        fun dp(value: Int): Int = (value * density).toInt()

        // Panel principal para contener todos los elementos
        val panel = UiFactory.panel(context, 3f).apply {
            setPadding(4, 8, 4, 8)
        }

        // Contenedor Vertical principal para estructurar las secciones de arriba a abajo
        val mainContainer = UiFactory.mainContainer(context).apply {
            setPadding(dp(8),dp(8),dp(8),dp(8))
        }

        //<editor-fold desc="Imagen e Info Principal">
        val headerContainer = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        //<editor-fold desc="Imagen de la runa">
        val imageRune = UiFactory.icon(
            context,
            rune.runeSet.idRuneResource,
            180,
            180,
            context.colorRes(R.color.background_primary),
        ).apply {setPadding(dp(10), dp(10), dp(10), dp(10))}
        headerContainer.addView(imageRune)
        //</editor-fold>

        //<editor-fold desc="Cabecera de la runa">
        val infoContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            weightSum = 4f // Para dividir el espacio en 4
            setPadding(dp(8), 0, 0, 0)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
        }

        // Cantidad de estrellas
        val tvStars = UiFactory.text(context,
            "★★★★★★",
            12f,
            context.colorRes(R.color.orange),
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        infoContainer.addView(tvStars)

        // Set
        val tvTitle = UiFactory.text(context,
            rune.runeSet.name,
            14f,
            context.colorRes(R.color.purple),
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        infoContainer.addView(tvTitle)

        // Rareza
        val tvTag = UiFactory.text(context,
            rune.rarity.name,
            10f,
            context.colorRes(rune.rarity.colorText),
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f).apply {
            setPadding(10, 4, 10, 4)
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                // Color de fondo del contenedor
                setColor(context.colorRes(rune.rarity.colorBackground))
                // Esquinas redondeadas en píxeles
                cornerRadius = 10f
            }
        }
        infoContainer.addView(tvTag)

        // Slot
        val tvSlot = UiFactory.text(context,
            "Slot ${rune.slot}",
            10f,
            Color.WHITE,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        infoContainer.addView(tvSlot)

        headerContainer.addView(infoContainer)
        //</editor-fold>

        // Linea vertical
        headerContainer.addView(DividerView(context).apply {
            setup(LineOrientation.VERTICAL, 4, context.colorRes(R.color.border),
                left = 16,
                right = 16
            )
        })

        //<editor-fold desc="Propiedad principal e innate">
        // Columna Izquierda: Propiedad Principal
        val mainStat = StatView(context).apply {
            headerText = "Stat Principal"
            secondText = rune.primaryStat()
            secondColor =Color.WHITE
            iconRes = rune.imgMainStat()
        }
        headerContainer.addView(mainStat)

        // Línea Divisoria Central
        headerContainer.addView(DividerView(context).apply {
            setup(LineOrientation.VERTICAL, 4, context.colorRes(R.color.border),
                left = 16,
                right = 16
            )
        })

        // Columna Derecha: Propiedad Innata
        val innateStat = StatView(context).apply {
            headerText = "Stat Innate"
            secondText = rune.innateStat()
            secondColor = context.colorRes(rune.getColorByInnateValue())
            iconRes = rune.imgInnateStat()
        }
        headerContainer.addView(innateStat)
        //</editor-fold>

        mainContainer.addView(headerContainer)
        //</editor-fold>

        // Línea divisoria
        mainContainer.addView(DividerView(context).apply {
            setup(LineOrientation.HORIZONTAL, 4, context.colorRes(R.color.border),
                top = 8,
                bottom = 8
            )
        })

        //<editor-fold desc="SubPropiedades">
        val subPropertiesCard = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            background = GradientDrawable().apply {
                setColor(context.colorRes(R.color.background_primary))
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f // Esquinas redondeadas en píxeles
            }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        //<editor-fold desc="Encabezados">
        val rowColumNames = UiFactory.row(context,5f).apply {
            setPadding(dp(8),dp(8),dp(8),dp(8))
        }

        // SubStat
        rowColumNames.addView(UiFactory.text(context,
            "SubStat",
            13f,
            Color.WHITE,
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            2f
        ))

        // Base
        rowColumNames.addView(UiFactory.text(context,
            "Base",
            13f,
            Color.WHITE,
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        ).apply { gravity = Gravity.CENTER })

        // GrindStone
        rowColumNames.addView(UiFactory.text(context,
            "GrindStone",
            13f,
            Color.WHITE,
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        ).apply { gravity = Gravity.CENTER })

        // Total
        rowColumNames.addView(UiFactory.text(context,
            "Total",
            13f,
            Color.WHITE,
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        ).apply { gravity = Gravity.CENTER })

        subPropertiesCard.addView(rowColumNames)
        //</editor-fold>

        // SubStat con la menor contribución de la runa
        val lowSubStatContribution = rune.subStats.minByOrNull { it.subStatCurrentContribution() }

        // La runa tiene una propiedad que viene una gema
        val hasSubStatEnchanted = rune.subStats.any {it.statType.isEnchanted}

        // SubStat que proviene de una gema
        val enchantedSubStat = rune.subStats.firstOrNull{it.statType.isEnchanted}

        // SubStats disponibles para esta runa por su slot
        val filteredSubStats: List<RuneStatType> =
            RuneStatType.entries.filter { it.slots.contains(rune.slot) } - rune.subStats.map { it.statType }

        // Cargar los subStats de la runa
        rune.subStats.forEachIndexed { index, subStat ->
            // Color que va a tener el icono y SubStat
            val color =
                // Si el SubStat es el más bajo y ninguna propiedad proviene de una gema, será rojo
                if((subStat == lowSubStatContribution) and (!hasSubStatEnchanted))
                    context.colorRes(R.color.light_red)
                else
                    // Si el SubStat no proviene de una gema, será blanco
                    if(!subStat.statType.isEnchanted)
                        Color.WHITE
                    // En caso contrario será naranja, indicando que proviene de una gema
                    else
                        context.colorRes(R.color.orange)

            val isEditable =
                // Si la runa no tiene aplicado un Enchanted sera editable el SubStat
                if(!hasSubStatEnchanted)
                    true
                else
                    // Si el tipo del SubStat que proviene de una gema es igual al SubStat
                    if (enchantedSubStat?.statType == subStat.statType)
                        true
                    else
                        false

            // Indica si es necesario mostrar los valores máximos si su valor es menor
            val showMaxValue =  subStat.hasGrindstone() and !subStat.hasMaxGrindstoneValue() and (subStat.grindstonevalue != 0)

            val rowSubStat = SubStatView(context).apply {
                iconRes = subStat.imgStat()
                nameStat = subStat.statType.displayText
                valueStat = subStat.textValueStat()
                colorSubStat = color
                currentGrindstone = subStat.textGrindstoneValue()
                colorGrinstone = context.colorRes(subStat.getColorByValueGrinstone())
                currentTotal = subStat.textTotalValue(showMaxValue)
                visibleMaxValue = showMaxValue
                maxGrindstone = subStat.textGrindstoneMaxValue()
                maxTotal = subStat.textTotalMaxValue()
                imEditable = isEditable
                availableSubStats = (filteredSubStats + subStat.statType)
            }.apply {
                setOnClickListener {
                    if (!editMode) return@setOnClickListener

                    if (!imEditable) return@setOnClickListener

                    Log.d("Tap","Tap en: ${nameStat}")
                    Log.d("Tap","SubStats disponibles: ${availableSubStats}")

                    showAttributeSelector(overlayView, availableSubStats) { selectedValue ->
                        Log.d("Tap","Seleccionado: ${selectedValue}")
                        Log.d("Tap","Seleccionado: ${selectedValue.enchantedMaxValue}")
                    }
                }
            }

            subPropertiesCard.addView(rowSubStat)

            // Agregar una mini línea divisoria gris entre cada fila, excepto en la última
            if (index < rune.subStats.size - 1) {
                subPropertiesCard.addView(DividerView(context).apply {
                    setup(LineOrientation.HORIZONTAL, 4, context.colorRes(R.color.border),
                        top = 8,
                        bottom = 8
                    )
                })
            }
        }

        mainContainer.addView(subPropertiesCard)
        //</editor-fold>

        // Línea divisoria
        mainContainer.addView(DividerView(context).apply {
            setup(LineOrientation.HORIZONTAL, 4, context.colorRes(R.color.border),
                top = 8,
                bottom = 8
            )
        })

        val footerContainer = UiFactory.row(context)

        //<editor-fold desc="Selector de Estrellas">
        val columnSelectorStar = UiFactory.column(
            context,
            context.colorRes(R.color.background_primary),
            1f).apply {
            setPadding(dp(8), dp(8), dp(8), dp(8))
        }

        val lbStars = UiFactory.text(context,
            "Estrellas",
            13f,
            Color.WHITE,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        columnSelectorStar.addView(lbStars)

        // Fila de botones de estrellas
        val rowBotones = UiFactory.row(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, dp(8), 0, 0) }
        }

        // Texto informativo final de cálculos

        val tvCalInfo = UiFactory.text(context,
            "(Los cálculos se realizan como 6★)",
            12f,
            Color.WHITE,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            top = 16
        ).apply {
            gravity = Gravity.CENTER
        }

        // Lista para controlar los estados visuales de los botones
        val listaBotones = ArrayList<Button>()

        // Función interna para actualizar laUI de las estrellas superiores de forma dinámica
        fun actualizarEstrellasUI(cantidad: Int) {
            tvStars.text = getStars(cantidad)

            tvCalInfo.text = "(Los cálculos se realizan como $cantidad★)"

            // Cambiar estados de los botones (el seleccionado se vuelve morado)
            listaBotones.forEachIndexed { index, button ->
                button.background = GradientDrawable().apply {
                    setColor(
                        if(index + 1 == cantidad) context.colorRes(R.color.orange) else context.colorRes(R.color.background_primary)
                    )
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 16f // Esquinas redondeadas en píxeles
                }

                button.setTextColor(
                    if(index + 1 == cantidad) Color.BLACK else Color.WHITE
                )
            }
        }

        // Crear dinámicamente los 6 botones
        for (i in 1..6) {
            val btn = Button(context, null, android.R.attr.button).apply {
                text = "${i}★"
                gravity = Gravity.CENTER
                textSize = 12f
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    50,
                    1f).
                apply {
                    //setMargins(4, 4, 4, 4)
                }
                setOnClickListener {
                    actualizarEstrellasUI(i)
                }
            }
            listaBotones.add(btn)
            rowBotones.addView(btn)
        }

        columnSelectorStar.addView(rowBotones)
        columnSelectorStar.addView(tvCalInfo)

        footerContainer.addView(columnSelectorStar)

        // Estado inicial por defecto: 6 Estrellas
        actualizarEstrellasUI(6)

        mainContainer.addView(footerContainer)
        //</editor-fold>

        // Línea Divisoria Central
        footerContainer.addView(DividerView(context).apply {
            setup(LineOrientation.VERTICAL, 4, context.colorRes(R.color.border),
                left = 16,
                right = 16
            )
        })

        //<editor-fold desc="Botones de acción">
        val columnSelectorLocation = UiFactory.column(context, weight=1f)

        val rowActionButtons = UiFactory.row(context,2f)

        // Botón Izquierdo: Volver atrás
        val btnBack = UiFactory.button(context,
            R.drawable.action_back,
            context.colorRes(R.color.button_dark_text),
            "Volver",
            13f,
            context.colorRes(R.color.button_dark_text),
            context.colorRes(R.color.button_dark_background),
            2f,
            context.colorRes(R.color.button_dark_text)
        ).apply {
            setOnClickListener {
                editMode = false
                onClose()
                onRemove()
            }
        }
        rowActionButtons.addView(btnBack)

        // Espacio responsivo entre botones
        rowActionButtons.addView(android.view.View(context).apply { layoutParams = LinearLayout.LayoutParams(dp(4), 1) })

        // Botón Derecho: Editar Runa
        val btnEdit = UiFactory.button(context,
            R.drawable.action_edit,
            context.colorRes(R.color.button_dark_text),
            "Editar",
            13f,
            context.colorRes(R.color.button_dark_text),
            context.colorRes(R.color.button_dark_background),
            2f,
            context.colorRes(R.color.button_dark_text),
        ).apply {
            setOnClickListener {
                editMode = true
                Toast.makeText(context, "Edición habilitada", Toast.LENGTH_SHORT).show()
            }
        }

        rowActionButtons.addView(btnEdit)

        columnSelectorLocation.addView(rowActionButtons)

        // Espaciador antes del botón de guardar
        columnSelectorLocation.addView(android.view.View(context).apply { layoutParams = LinearLayout.LayoutParams(1, dp(4)) })

        // Botón Inferior: Guardar
        val btnSave = UiFactory.button(context,
            R.drawable.action_save,
            context.colorRes(R.color.background_primary),
            "Guardar",
            13f,
            context.colorRes(R.color.button_light_text),
            context.colorRes(R.color.button_light_background),
            8f,
            context.colorRes(R.color.dark_border),
            intArrayOf(
                context.colorRes(R.color.button_light_background),
                context.colorRes(R.color.border)
            )
        ).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {

            }
        }
        columnSelectorLocation.addView(btnSave)

        footerContainer.addView(columnSelectorLocation)
        //</editor-fold>

        // Agregamos el contenedor al panel
        panel.addView(mainContainer)

        return panel
    }

    @SuppressLint("SetTextI18n")
    fun createScanOverlayRightPanel(
        context: Context,
        rune: Rune
    ): FrameLayout {
        // FUNCIÓN AUXILIAR: Convierte valores DP a Píxeles reales según la pantalla del dispositivo
        val density = context.resources.displayMetrics.density
        fun dp(value: Int): Int = (value * density).toInt()

        // Panel principal para contener todos los elementos
        val panel = UiFactory.panel(context, 1f).apply {
            setPadding(4, 8, 8, 8)
        }

        // Contenedor Vertical principal para estructurar las secciones de arriba a abajo
        val mainContainer = UiFactory.mainContainer(context).apply {
            setPadding(dp(8),dp(8),dp(8),dp(8))
        }

        val titleContainer = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val imgEficienciaIcon = ImageView(context).apply {
            setImageResource(R.drawable.icon_efficiency)
            layoutParams = LinearLayout.LayoutParams(50, 50) // Escalado a DP
            scaleType = ImageView.ScaleType.FIT_CENTER
            setColorFilter(Color.WHITE)
        }
        titleContainer.addView(imgEficienciaIcon)

        val tvSectionTitle = TextView(context).apply {
            text = "EFICIENCIA"
            setTextColor(Color.WHITE)
            textSize = 15f // Las fuentes en Android ya se auto-escalan de forma nativa (SP)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(4),0,0,0)
        }
        titleContainer.addView(tvSectionTitle)
        mainContainer.addView(titleContainer)

        // Línea divisoria debajo del título
        mainContainer.addView(DividerView(context).apply {
            setup(LineOrientation.HORIZONTAL, 4, context.colorRes(R.color.border),
                top = 8,
                bottom = 8
            )
        })

        baseCard = ProgressStatView(context).apply {
            title = "Eficiencia Base"
            percentage = rune.baseEfficiency()
            progressColor = Color.WHITE
            barHeight = dp(6)
        }.apply { setPadding(dp(8), dp(8), dp(8), dp(8)) }

        mainContainer.addView(baseCard)

        val hasGrindstone = rune.subStats.any {it.grindstonevalue != 0}

        val currentEfficiency = rune.currentEfficiency()
        val maxEfficiency = rune.maxEfficiency()

        val isMaxEfficiency = currentEfficiency == maxEfficiency

        actualCard = ProgressStatView(context).apply {
            title = "Eficiencia Actual"
            percentage = currentEfficiency
            progressColor = context.colorRes(R.color.orange)
            barHeight = dp(6)
        }.apply {
            setPadding(dp(8), dp(8), dp(8), dp(8))
            visibility =  if(hasGrindstone and !isMaxEfficiency) View.VISIBLE else View.GONE
        }
        mainContainer.addView(actualCard)

        maxCard = ProgressStatView(context).apply {
            title = "Eficiencia Máxima"
            percentage = maxEfficiency
            progressColor = context.colorRes(R.color.green)
            barHeight = dp(6)
        }.apply { setPadding(dp(8), dp(8), dp(8), dp(8)) }
        mainContainer.addView(maxCard)

        panel.addView(mainContainer)

        return panel
    }

    fun showAttributeSelector(containerFrameLayout: FrameLayout,availableSubStats: List<RuneStatType>, onSelected: (RuneStatType) -> Unit) {
        val context = containerFrameLayout.context

        // Contenedor principal de la ventana
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(context, 16), dp(context, 16), dp(context, 16), dp(context, 16))

            // Fondo
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(context.colorRes(R.color.background_secondary))
                setStroke(4, context.colorRes(R.color.border))
                cornerRadius = dp(context, 12).toFloat()
            }

            // Layout params para centrarlo dentro del FrameLayout
            layoutParams = FrameLayout.LayoutParams(
                dp(context, 300),
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }

            // Consumir clics para que no traspasen al fondo
            isClickable = true
            isFocusable = true
        }

        // GridLayout de 2 columnas para las opciones
        val gridLayout = GridLayout(context).apply {
            columnCount = 2
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Lista de opciones a mostrar
        val options = listOf(
            "VEL" to RuneStatType.SPD,
            "HP +" to RuneStatType.HP,
            "HP %" to RuneStatType.HP_PERCENT,
            "ATQ +" to RuneStatType.ATK,
            "ATQ %" to RuneStatType.ATK_PERCENT,
            "DEF +" to RuneStatType.DEF,
            "DEF %" to RuneStatType.DEF_PERCENT,
            "Tasa CRí" to RuneStatType.CRIT_RATE,
            "Daño CRí" to RuneStatType.CRIT_DAMAGE,
            "RES" to RuneStatType.RESISTANCE,
            "Precisión" to RuneStatType.ACCURACY,
            "X" to RuneStatType.UNKNOWN
        )

        options.forEach { (statName, statType) ->
            val textView = TextView(context).apply {
                this.text = statName
                setTextColor(context.colorRes(R.color.button_dark_text))
                textSize = 16f
                setTypeface(null, Typeface.BOLD)
                gravity = Gravity.CENTER
                setPadding(dp(context, 12), dp(context, 12), dp(context, 12), dp(context, 12))

                // Selector de feedback al tocar (efecto ripple nativo si está disponible)
                val outValue = android.util.TypedValue()
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                setBackgroundResource(outValue.resourceId)

                // Configurar el click para retornar el valor y cerrar el diálogo
                setOnClickListener {
                    onSelected(statType)
                    containerFrameLayout.removeView(dialogLayout)
                }

                // Distribuir el ancho equitativamente entre las 2 columnas
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(dp(context, 4), dp(context, 4), dp(context, 4), dp(context, 4))
                }
            }
            gridLayout.addView(textView)
        }

        // Ensamblar vistas
        dialogLayout.addView(gridLayout)
        containerFrameLayout.addView(dialogLayout)
    }

    // Función auxiliar para convertir Dp a Px de forma segura
    private fun dp(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}